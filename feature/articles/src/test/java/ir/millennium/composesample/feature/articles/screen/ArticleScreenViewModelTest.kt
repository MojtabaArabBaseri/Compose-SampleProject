package ir.millennium.composesample.feature.articles.screen

import androidx.paging.PagingData
import ir.millennium.composesample.core.domain.usecase.ArticlesUseCase
import ir.millennium.composesample.core.model.ArticleEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.reset
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ArticleScreenViewModelTest {

    @Mock
    lateinit var mockArticlesUseCase: ArticlesUseCase

    private lateinit var viewModel: ArticleScreenViewModel

    private lateinit var fakePagingData: PagingData<ArticleEntity>

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockitoAnnotations.openMocks(this)
        viewModel = ArticleScreenViewModel(mockArticlesUseCase)
    }

    @Test
    fun `lazyColumn state should be initialized correctly`() {
        assertNotNull(viewModel.stateLazyColumn)
        assertEquals(0, viewModel.stateLazyColumn.firstVisibleItemIndex)
        assertEquals(0, viewModel.stateLazyColumn.firstVisibleItemScrollOffset)
    }

    @Test
    fun `verify ViewModel initializes articlePagingFlow`() = runTest {
        fakePagingData = PagingData.from(listOf(mock()))
        whenever(mockArticlesUseCase.invoke()).thenReturn(flowOf(fakePagingData))
        advanceUntilIdle()
        assertNotNull(viewModel.articlePagingFlow)
    }

    @Test
    fun `verify articlesUseCase is invoked on ViewModel creation`() = runTest {
        fakePagingData = PagingData.from(listOf(mock()))
        whenever(mockArticlesUseCase.invoke()).thenReturn(flowOf(fakePagingData))

        ArticleScreenViewModel(mockArticlesUseCase)

        verify(mockArticlesUseCase, atLeastOnce()).invoke()
        verifyNoMoreInteractions(mockArticlesUseCase)
    }

//    @Test
//    fun `verify pagingData is mapped to article items`() = runTest {
//        val fakeArticleEntity = mock<ArticleEntity>()
//        val fakeArticleItem = fakeArticleEntity.mapToArticleItem()
//        val fakePagingData = PagingData.from(listOf(fakeArticleEntity))
//
//        whenever(mockArticlesUseCase.invoke()).thenReturn(flowOf(fakePagingData))
//
//        val viewModel = ArticleScreenViewModel(mockArticlesUseCase)
//        val result = viewModel.articlePagingFlow.take(1).collectLatest {
//
//        }
//
//        // Assert
//        assertEquals(listOf(fakeArticleItem), result)
//    }
//
//    @Test
//    fun `articlePagingFlow should collect articles correctly`() = runTest {
//        fakePagingData =
//            PagingData.from(
//                listOf(
//                    ArticleEntity(title = "title1"),
//                    ArticleEntity(title = "title2")
//                )
//            )
//        val expectedMappedData =
//            listOf(ArticleEntity(title = "title1"), ArticleEntity(title = "title2"))
//
//        whenever(mockArticlesUseCase.invoke()).thenReturn(flowOf(fakePagingData))
//
//        viewModel = ArticleScreenViewModel(mockArticlesUseCase)
//
//        val actualData = mutableListOf<ArticleItem>()
//        val job = launch {
//            viewModel.articlePagingFlow.take(1).collectLatest { pagingData ->
//                pagingData.map { articleItem ->
//                    actualData.add(articleItem)
//                }
//            }
//        }
//        advanceUntilIdle()
//        job.cancel()
//        assertEquals(expectedMappedData, actualData)
//    }

    @Test
    fun `verify articlePagingFlow is cached in viewModelScope`() = runTest {

        fakePagingData = PagingData.from(listOf(mock()))
        whenever(mockArticlesUseCase.invoke()).thenReturn(flowOf(fakePagingData))

        val viewModel = ArticleScreenViewModel(mockArticlesUseCase)

        advanceUntilIdle()
        viewModel.articlePagingFlow.take(1).collectLatest { pagingData ->
            assertNotNull(pagingData)
        }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        reset(mockArticlesUseCase)
    }
}