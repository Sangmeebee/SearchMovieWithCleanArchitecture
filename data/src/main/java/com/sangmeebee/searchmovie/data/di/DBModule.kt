package com.sangmeebee.searchmovie.data.di

import android.content.Context
import androidx.room.Room
import com.sangmeebee.searchmovie.data.db.AppDataBase
import com.sangmeebee.searchmovie.data.db.MIGRATION_1_2
import com.sangmeebee.searchmovie.data.db.MovieBookmarkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

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