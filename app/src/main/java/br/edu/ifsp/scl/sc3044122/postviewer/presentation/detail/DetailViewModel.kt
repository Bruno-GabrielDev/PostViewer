package br.edu.ifsp.scl.sc3044122.postviewer.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.sc3044122.postviewer.data.PostRepository
import br.edu.ifsp.scl.sc3044122.postviewer.domain.Comment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/**
 * ViewModel da tela de detalhes do post.
 * Combina comentários da API com comentários locais via Flow.
 */
class DetailViewModel(
    private val repository: PostRepository,
    private val postId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState

    private val _apiComments = MutableStateFlow<List<Comment>>(emptyList())

    init {
        loadComments()
    }

    /**
     * Exposto para a UI poder chamar retry em caso de erro.
     * Delega para loadComments() para que ele permaneca privado.
     */
    fun retry() = loadComments()

    private fun loadComments() {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                _apiComments.value = repository.getApiComments(postId)

                combine(
                    _apiComments,
                    repository.getLocalComments(postId)
                ) { apiComments, localComments ->
                    apiComments + localComments
                }.collect { allComments ->
                    _uiState.value = DetailUiState.Success(allComments)
                }

            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error("Erro ao carregar comentários: ${e.message}")
            }
        }
    }

    fun addLocalComment(name: String, body: String) {
        if (name.isBlank() || body.isBlank()) return
        viewModelScope.launch {
            repository.insertLocalComment(postId, name, body)
        }
    }

    class Factory(
        private val repository: PostRepository,
        private val postId: Int
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(repository, postId) as T
        }
    }
}

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val comments: List<Comment>) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}