package ir.millennium.composesample.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.millennium.composesample.core.common.repository.ArticleRepository
import ir.millennium.composesample.core.data.repository.ArticleRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    @Singleton
    abstract fun bindsArticleRepository(
        remoteRepositoryImpl: ArticleRepositoryImpl
    ): ArticleRepository
}