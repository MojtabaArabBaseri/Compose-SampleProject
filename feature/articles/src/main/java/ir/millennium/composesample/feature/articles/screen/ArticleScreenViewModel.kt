package ir.millennium.composesample.feature.articles.screen

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.composesample.core.domain.usecase.ArticlesUseCase
import ir.millennium.composesample.core.network.mapper.mapToArticleItem
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
open class ArticleScreenViewModel @Inject constructor(
    articlesUseCase: ArticlesUseCase
) : ViewModel() {

    val stateLazyColumn = LazyListState()

    val articlePagingFlow = articlesUseCase.invoke()
        .map { pagingData ->
            pagingData.map { it.mapToArticleItem() }
        }
        .cachedIn(viewModelScope)
}
