package ir.millennium.composesample.core.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ResponseArticlesModel(

    @field:SerializedName("totalResults")
    val totalResults: Int? = null,

    @field:SerializedName("articles")
    val articles: List<ArticleItem>? = null,

    @field:SerializedName("status")
    val status: String? = null
)

@Parcelize
data class Source(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: String? = null
) : Parcelable
