package ir.millennium.composesample.core.domain.usecase

import ir.millennium.composesample.core.common.repository.ArticleRepository
import javax.inject.Inject

open class ArticlesUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    operator fun invoke() = articleRepository.getArticleWithPager().flow
}