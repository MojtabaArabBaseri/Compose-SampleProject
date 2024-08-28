package ir.millennium.composesample.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tbl_Article")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val author: String? = "",
    val title: String? = "",
    val description: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    val publishedAt: String? = "",
    val content: String? = ""
)
