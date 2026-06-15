package br.edu.ifsp.scl.sc3044122.postviewer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO Room para operações com comentários locais.
 */
@Dao
interface LocalCommentDao {

    @Query("SELECT * FROM local_comments WHERE postId = :postId")
    fun getCommentsByPostId(postId: Int): Flow<List<LocalCommentEntity>>

    @Insert
    suspend fun insertComment(comment: LocalCommentEntity)
}