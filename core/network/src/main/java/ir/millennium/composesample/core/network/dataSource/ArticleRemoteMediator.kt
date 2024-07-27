package ir.millennium.composesample.core.network.dataSource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import ir.millennium.composesample.core.database.AppDatabase
import ir.millennium.composesample.core.database.model.ArticleEntity
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
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }
            val headerMap = mutableMapOf<String, Any>()
            headerMap["apiKey"] = Constants.API_KEY
            headerMap["from"] = "2023-07-00"
            headerMap["q"] = "tesla"

            val movieListModel = apiService.getArticlesWithPager(
                headerMap = headerMap,
                page = loadKey,
            )

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.articleDao().clearAll()
                }

                val movieEntityList = movieListModel.articles?.map { it.mapToArticleEntity() }
                movieEntityList?.let { appDatabase.articleDao().upsertAll(it) }
            }

            MediatorResult.Success(
                endOfPaginationReached = movieListModel.articles?.isEmpty()!!
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}