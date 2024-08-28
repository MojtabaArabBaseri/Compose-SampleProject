package ir.millennium.composesample.core.data.repository

import androidx.paging.Pager
import ir.millennium.composesample.core.common.repository.ArticleRepository
import ir.millennium.composesample.core.model.ArticleEntity
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val pager: Pager<Int, ArticleEntity>
) : ArticleRepository {
    override fun getArticleWithPager(): Pager<Int, ArticleEntity> {
        return pager
    }
}