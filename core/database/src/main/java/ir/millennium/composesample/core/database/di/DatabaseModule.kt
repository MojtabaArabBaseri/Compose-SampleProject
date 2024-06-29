package ir.millennium.composesample.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.millennium.composesample.core.database.AppDatabase
import ir.millennium.composesample.core.database.Constants.APP_DATABASE_NAME
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room
        .databaseBuilder(context, AppDatabase::class.java, APP_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDatabaseDao(db: AppDatabase) = db.articleDao()

}