package br.edu.ifsp.scl.sc3044122.postviewer.data.remote

import br.edu.ifsp.scl.sc3044122.postviewer.domain.Comment
import com.google.gson.annotations.SerializedName
data class CommentDto(
    @SerializedName("id")     val id: Int,
    @SerializedName("postId") val postId: Int,
    @SerializedName("name")   val name: String,
    @SerializedName("email")  val email: String,
    @SerializedName("body")   val body: String
) {
    fun toDomain() = Comment(
        id = id,
        postId = postId,
        name = name,
        email = email,
        body = body,
        isLocal = false
    )
}