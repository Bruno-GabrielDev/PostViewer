package br.edu.ifsp.scl.sc3044122.postviewer.presentation.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.sc3044122.postviewer.data.PostRepository
import br.edu.ifsp.scl.sc3044122.postviewer.domain.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel da tela de lista de posts.
 * Gerencia o estado via StateFlow e carrega posts da API.
 */
class PostsViewModel(private val repository: PostRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<PostsUiState>(PostsUiState.Loading)
    val uiState: StateFlow<PostsUiState> = _uiState

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = PostsUiState.Loading
            try {
                repository.getPostsWithCommentCounts().collect { posts ->
                    _uiState.value = PostsUiState.Success(posts)
                }
            } catch (e: Exception) {
                _uiState.value = PostsUiState.Error("Erro ao carregar posts: ${e.message}")
            }
        }
    }

    class Factory(private val repository: PostRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return PostsViewModel(repository) as T
        }
    }
}

sealed class PostsUiState {
    object Loading : PostsUiState()
    data class Success(val posts: List<Post>) : PostsUiState()
    data class Error(val message: String) : PostsUiState()
}