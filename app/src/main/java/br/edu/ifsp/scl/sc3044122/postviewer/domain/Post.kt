package br.edu.ifsp.scl.sc3044122.postviewer.domain

data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)