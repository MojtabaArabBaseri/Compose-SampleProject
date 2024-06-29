package ir.millennium.composesample.core.domain.usecase

import androidx.paging.Pager
import ir.millennium.composesample.core.database.model.ArticleEntity
import javax.inject.Inject

open class GetArticlesUseCase @Inject constructor(
    private val pager: Pager<Int, ArticleEntity>
) {
    operator fun invoke() = pager.flow
}