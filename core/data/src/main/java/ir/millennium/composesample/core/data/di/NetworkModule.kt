package ir.millennium.composesample.core.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.millennium.composesample.core.data.repository.RemoteRepositoryImpl
import ir.millennium.composesample.core.network.dataSource.ApiService
import ir.millennium.composesample.core.network.di.qualifiers.ApiCaching
import ir.millennium.composesample.core.network.di.qualifiers.RemoteRepositoryCaching
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // Setup For Non Caching Webservice
    @Provides
    @Singleton
    fun provideRemoteRepository(apiService: ApiService): RemoteRepositoryImpl =
        RemoteRepositoryImpl(apiService)

    // Setup For Caching Webservice
    @Provides
    @Singleton
    @RemoteRepositoryCaching
    fun provideRemoteRepositoryCaching(@ApiCaching apiService: ApiService): RemoteRepositoryImpl =
        RemoteRepositoryImpl(apiService)

}