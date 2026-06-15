package br.edu.ifsp.scl.sc3044122.postviewer.data.remote

import br.edu.ifsp.scl.sc3044122.postviewer.domain.Post
import com.google.gson.annotations.SerializedName
data class PostDto(
    @SerializedName("id")     val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("title")  val title: String,
    @SerializedName("body")   val body: String
) {
    fun toDomain() = Post(id = id, userId = userId, title = title, body = body)
}