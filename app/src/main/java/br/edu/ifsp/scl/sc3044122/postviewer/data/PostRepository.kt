package br.edu.ifsp.scl.sc3044122.postviewer.data

import br.edu.ifsp.scl.sc3044122.postviewer.data.local.AppDatabase
import br.edu.ifsp.scl.sc3044122.postviewer.data.local.LocalCommentEntity
import br.edu.ifsp.scl.sc3044122.postviewer.data.remote.ApiService
import br.edu.ifsp.scl.sc3044122.postviewer.domain.Comment
import br.edu.ifsp.scl.sc3044122.postviewer.domain.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repositório responsável por orquestrar dados da API (Retrofit)
 * e do banco de dados local (Room).
 */
class PostRepository(
    private val api: ApiService,
    private val db: AppDatabase
) {
    fun getPostsWithCommentCounts(): Flow<List<Post>> {
        return db.localCommentDao().getCommentCountsByPost().map { counts ->
            val countsMap = counts.associate { it.postId to it.count }
            val posts = api.getPosts().map { it.toDomain() }
            posts.map { post ->
                post.copy(localCommentCount = countsMap[post.id] ?: 0)
            }
        }
    }

    suspend fun getApiComments(postId: Int): List<Comment> =
        api.getComments(postId).map { it.toDomain() }

    fun getLocalComments(postId: Int): Flow<List<Comment>> =
        db.localCommentDao()
            .getCommentsByPostId(postId)
            .map { list -> list.map { it.toDomain() } }

    suspend fun insertLocalComment(postId: Int, name: String, body: String) {
        db.localCommentDao().insertComment(
            LocalCommentEntity(
                postId = postId,
                name = name,
                email = "local@postviewer.app",
                body = body
            )
        )
    }
}