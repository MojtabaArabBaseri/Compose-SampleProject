package ir.millennium.composesample.core.common.repository

import androidx.paging.Pager
import ir.millennium.composesample.core.model.ArticleEntity

interface ArticleRepository {
    fun getArticleWithPager(): Pager<Int, ArticleEntity>
}