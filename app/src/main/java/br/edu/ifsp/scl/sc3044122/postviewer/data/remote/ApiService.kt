package br.edu.ifsp.scl.sc3044122.postviewer.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    /** Retorna a lista de todos os posts */
    @GET("posts")
    suspend fun getPosts(): List<PostDto>

    /** Retorna os comentários de um post específico */
    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") postId: Int): List<CommentDto>
}
