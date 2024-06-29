package ir.millennium.composesample.core.network.mapper

import ir.millennium.composesample.core.network.model.ArticleItem
import ir.millennium.composesample.core.database.model.ArticleEntity


fun ArticleEntity.mapToArticleItem() =
    ArticleItem(
        title = this.title,
        publishedAt = this.publishedAt,
        author = this.author,
        urlToImage = this.urlToImage,
        description = this.description,
        url = this.url,
        content = this.content
    )

fun ArticleItem.mapToArticleEntity() =
    ArticleEntity(
        title = this.title,
        publishedAt = this.publishedAt,
        author = this.author,
        urlToImage = this.urlToImage,
        description = this.description,
        url = this.url,
        content = this.content
    )
