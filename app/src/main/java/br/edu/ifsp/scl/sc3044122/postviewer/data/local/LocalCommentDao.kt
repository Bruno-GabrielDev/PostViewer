package br.edu.ifsp.scl.sc3044122.postviewer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.edu.ifsp.scl.sc3044122.postviewer.domain.Comment

/**
 * Entidade Room para comentários adicionados localmente pelo usuário.
 * Persiste entre sessões do app.
 */
@Entity(tableName = "local_comments")
data class LocalCommentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
) {
    fun toDomain() = Comment(
        id = id,
        postId = postId,
        name = name,
        email = email,
        body = body,
        isLocal = true
    )

    data class PostCommentCount(
        val postId: Int,
        val count: Int
    )
}