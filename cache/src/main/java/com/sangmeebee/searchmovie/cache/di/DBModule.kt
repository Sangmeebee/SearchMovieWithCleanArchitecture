package com.sangmeebee.searchmovie.cache.di

import android.content.Context
import androidx.room.Room
import com.sangmeebee.searchmovie.cache.db.AppDataBase
import com.sangmeebee.searchmovie.cache.db.MIGRATION_1_2
import com.sangmeebee.searchmovie.cache.db.MovieBookmarkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DBModule {

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): AppDataBase = Room
        .databaseBuilder(context, AppDataBase::class.java, "SearchMovie.db")
        .addMigrations(MIGRATION_1_2)
        .build()

    @Singleton
    @Provides
    fun provideMovieDao(database: AppDataBase): MovieBookmarkDao = database.movieBookmarkDao()
}