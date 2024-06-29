package ir.millennium.composesample.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ir.millennium.composesample.core.database.model.ArticleEntity

@Dao
interface ArticleDao {

    @Upsert
    suspend fun upsertAll(articles: List<ArticleEntity>)

    @Query("SELECT * FROM tbl_Article")
    fun pagingSource(): PagingSource<Int, ArticleEntity>

    @Query("DELETE FROM tbl_Article")
    suspend fun clearAll()
}