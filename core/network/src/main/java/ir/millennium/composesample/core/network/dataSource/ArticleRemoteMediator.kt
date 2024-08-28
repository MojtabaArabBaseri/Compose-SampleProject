package ir.millennium.composesample.core.network.dataSource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import ir.millennium.composesample.core.database.AppDatabase
import ir.millennium.composesample.core.model.ArticleEntity
import ir.millennium.composesample.core.network.Constants
import ir.millennium.composesample.core.network.mapper.mapToArticleEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val appDatabase: AppDatabase,
    private val apiService: ApiService
) : RemoteMediator<Int, ArticleEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.let {
                        (it.id / state.config.pageSize) + 1
                    } ?: 1
                }
            }

            val headerMap = hashMapOf(
                "apiKey" to Constants.API_KEY,
                "from" to "2023-07-00",
                "q" to "tesla"
            )

            val movieListModel = apiService.getArticlesWithPager(
                headerMap = headerMap,
                page = loadKey,
            )

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.articleDao().clearAll()
                }
                movieListModel.articles?.map { it.mapToArticleEntity() }
                    ?.let { appDatabase.articleDao().upsertAll(it) }
            }

            MediatorResult.Success(endOfPaginationReached = movieListModel.articles?.isEmpty() == true)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}