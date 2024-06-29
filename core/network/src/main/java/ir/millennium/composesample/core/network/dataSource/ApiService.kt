package ir.millennium.composesample.core.network.dataSource

import ir.millennium.composesample.core.network.model.ResponseArticlesModel
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {

    @GET("everything")
    suspend fun getArticlesWithPager(
        @QueryMap headerMap: MutableMap<String, Any>,
        @Query("page") page: Int
    ): ResponseArticlesModel
}