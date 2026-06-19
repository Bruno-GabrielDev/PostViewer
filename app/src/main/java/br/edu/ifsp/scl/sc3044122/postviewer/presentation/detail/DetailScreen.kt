package br.edu.ifsp.scl.sc3044122.postviewer.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifsp.scl.sc3044122.postviewer.domain.Comment

/**
 * Tela 2 — Detalhes do Post.
 * Exibe comentários da API + comentários locais.
 * Permite adicionar novo comentário local.
 * Trata estados de loading, sucesso e erro (com retry).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    postTitle: String,
    viewModel: DetailViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var newCommentName by remember { mutableStateOf("") }
    var newCommentBody by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Comentários", maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Título do post
            Text(
                text = postTitle,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            HorizontalDivider()

            // Estado da lista de comentários
            Box(modifier = Modifier.weight(1f)) {
                when (val state = uiState) {

                    is DetailUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    is DetailUiState.Success -> {
                        LazyColumn {
                            items(state.comments) { comment ->
                                CommentItem(comment = comment)
                                HorizontalDivider()
                            }
                        }
                    }

                    is DetailUiState.Error -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = state.message,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(16.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            // Chama retry() que delega para loadComments() privado
                            Button(onClick = { viewModel.retry() }) {
                                Text("Tentar novamente")
                            }
                        }
                    }
                }
            }

            // Seção de adicionar comentário local
            HorizontalDivider()
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Adicionar comentário", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = newCommentName,
                    onValueChange = { newCommentName = it },
                    label = { Text("Seu nome") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = newCommentBody,
                    onValueChange = { newCommentBody = it },
                    label = { Text("Comentário") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        viewModel.addLocalComment(newCommentName, newCommentBody)
                        newCommentName = ""
                        newCommentBody = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = newCommentName.isNotBlank() && newCommentBody.isNotBlank()
                ) {
                    Text("Enviar comentário")
                }
            }
        }
    }
}

/**
 * Item individual de comentário.
 * Comentários locais exibem badge "Local".
 */
@Composable
fun CommentItem(comment: Comment) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = comment.name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )
            if (comment.isLocal) {
                Badge { Text("Local") }
            }
        }
        Text(
            text = comment.email,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = comment.body, fontSize = 14.sp)
    }
}

