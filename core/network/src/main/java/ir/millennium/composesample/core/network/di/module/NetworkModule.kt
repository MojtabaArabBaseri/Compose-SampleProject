package ir.millennium.composesample.core.network.di.module

import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.millennium.composesample.core.database.AppDatabase
import ir.millennium.composesample.core.database.model.ArticleEntity
import ir.millennium.composesample.core.network.Constants
import ir.millennium.composesample.core.network.Constants.CACHE_SIZE_FOR_RETROFIT
import ir.millennium.composesample.core.network.Constants.HEADER_CACHE_CONTROL
import ir.millennium.composesample.core.network.Constants.HEADER_PRAGMA
import ir.millennium.composesample.core.network.dataSource.ApiService
import ir.millennium.composesample.core.network.dataSource.ArticleRemoteMediator
import ir.millennium.composesample.core.network.di.qualifiers.ApiCaching
import ir.millennium.composesample.core.network.di.qualifiers.OkHttpClientCaching
import ir.millennium.composesample.core.network.di.qualifiers.OnlineInterceptor
import ir.millennium.composesample.core.network.di.qualifiers.RetrofitCaching
import ir.millennium.composesample.core.utils.utils.AuxiliaryFunctionsManager
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideMoviePager(
        appDatabase: AppDatabase,
        apiService: ApiService
    ): Pager<Int, ArticleEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = ArticleRemoteMediator(
                appDatabase = appDatabase,
                apiService = apiService
            ),
            pagingSourceFactory = {
                appDatabase.articleDao().pagingSource()
            }
        )
    }


    @Singleton
    @Provides
    fun provideBuildApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient.Builder,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASIC_URL)
        .client(okHttpClient.build())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @OnlineInterceptor onlineInterceptor: Interceptor,
        offlineInterceptor: Interceptor
    ): OkHttpClient.Builder {
        return try {
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.addInterceptor(httpLoggingInterceptor)
                .addInterceptor(onlineInterceptor)
                .addInterceptor(offlineInterceptor)
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true }
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Singleton
    @Provides
    @ApiCaching
    fun provideBuildApiServiceCaching(@RetrofitCaching retrofitCaching: Retrofit): ApiService {
        return retrofitCaching.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    @RetrofitCaching
    fun provideRetrofitCaching(
        @OkHttpClientCaching okHttpClient: OkHttpClient.Builder,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASIC_URL)
        .client(okHttpClient.build())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    @Singleton
    @Provides
    @OkHttpClientCaching
    fun provideOkHttpClientCaching(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @OnlineInterceptor onlineInterceptor: Interceptor,
        offlineInterceptor: Interceptor,
        cache: Cache
    ): OkHttpClient.Builder {
        return try {
            val builder = OkHttpClient.Builder()
            builder
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(onlineInterceptor)
                .cache(cache)
                .addInterceptor(offlineInterceptor)
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .hostnameVerifier { _, _ -> true }
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }


    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor { message -> Log.d("Data On Http WebService:", message) }
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    @OnlineInterceptor
    fun provideOnlineHttpInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val response = chain.proceed(chain.request())
            val cacheControl: CacheControl = CacheControl.Builder()
                .maxAge(1, TimeUnit.DAYS)
                .build()
            response.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .header(
                    HEADER_CACHE_CONTROL,
                    cacheControl.toString()
                )
                .build()
        }
    }

    @Singleton
    @Provides
    fun provideOfflineIntercepter(
        @ApplicationContext context: Context,
        auxiliaryFunctionsManager: AuxiliaryFunctionsManager
    ): Interceptor = Interceptor { chain ->
        val originalResponse: Response = chain.proceed(chain.request())
        if (auxiliaryFunctionsManager.isNetworkConnected(context)) {
            val maxAge = 60 * 60 * 24 // read from cache for 1 day
            originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        } else {
            val maxStale = 60 * 60 * 24 * 7 // tolerate one weeks stale
            originalResponse.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }
    }

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache =
        Cache(File(context.cacheDir, "SampleComposeProjectCache"), CACHE_SIZE_FOR_RETROFIT)

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().serializeNulls().create()

}