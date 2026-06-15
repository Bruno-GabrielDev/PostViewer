package br.edu.ifsp.scl.sc3044122.postviewer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalCommentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun localCommentDao(): LocalCommentDao
}
