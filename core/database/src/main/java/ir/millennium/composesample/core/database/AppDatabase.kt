package ir.millennium.composesample.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.millennium.composesample.core.database.dao.ArticleDao
import ir.millennium.composesample.core.model.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}

