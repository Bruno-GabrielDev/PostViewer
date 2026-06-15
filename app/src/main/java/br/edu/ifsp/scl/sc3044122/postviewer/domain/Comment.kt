package br.edu.ifsp.scl.sc3044122.postviewer.domain

data class Comment(
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String,
    val isLocal: Boolean = false  // true se for adicionado pelo usuário
)