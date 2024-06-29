package ir.millennium.composesample.feature.articles.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ir.millennium.composesample.core.network.model.ArticleItem
import ir.millennium.composesample.feature.articles.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArticleScreen(
    articleScreenViewModel: ArticleScreenViewModel,
    snackbarHostState: SnackbarHostState,
    navToDetailArticleScreen: (articleItemJson: String) -> Unit
) {

    val articleList = articleScreenViewModel.articlePagingFlow.collectAsLazyPagingItems()

    val swipeRefreshState = rememberSwipeRefreshState(false)

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {

        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            state = swipeRefreshState,
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    scale = true,
                    backgroundColor = MaterialTheme.colorScheme.onBackground,
                    contentColor = MaterialTheme.colorScheme.background
                )
            },
            indicatorPadding = PaddingValues(55.dp),
            onRefresh = { articleList.refresh() }
        ) {

            if (articleList.loadState.refresh is LoadState.Loading) {
                swipeRefreshState.isRefreshing = true
            } else {
                swipeRefreshState.isRefreshing = false
                LazyColumn(
                    contentPadding = PaddingValues(top = 65.dp),
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxSize(),
                    state = articleScreenViewModel.stateLazyColumn,
                ) {
                    items(articleList.itemCount) { index ->
                        rowArticle(
                            articleList[index]!!,
                            navToDetailArticleScreen = { articleItemJson ->
                                navToDetailArticleScreen(articleItemJson)
                            })
                    }

                    item {
                        if (articleList.loadState.append is LoadState.Loading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(70.dp)
                                    .padding(16.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .align(Alignment.Center),
                                    color = MaterialTheme.colorScheme.primary,
                                    strokeWidth = 6.dp
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = articleList.loadState) {
        if (articleList.loadState.refresh is LoadState.Error) {
            (articleList.loadState.refresh as LoadState.Error).error.message?.let {
                handleError(
                    context,
                    snackbarHostState,
                    coroutineScope,
                    it,
                    articleList
                )
            }
        }

        if (articleList.loadState.append is LoadState.Error) {
            (articleList.loadState.append as LoadState.Error).error.message?.let {
                handleError(
                    context,
                    snackbarHostState,
                    coroutineScope,
                    it,
                    articleList
                )
            }
        }
    }
}

fun handleError(
    context: Context,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    message: String,
    movieList: LazyPagingItems<ArticleItem>
) {
    coroutineScope.launch {
        snackbarHostState.currentSnackbarData?.dismiss()
        when (snackbarHostState.showSnackbar(message, context.getString(R.string.retry))) {
            SnackbarResult.Dismissed -> {}
            SnackbarResult.ActionPerformed -> {
                movieList.retry()
            }
        }
    }
}

