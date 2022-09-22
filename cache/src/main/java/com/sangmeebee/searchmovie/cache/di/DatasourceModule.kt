package com.sangmeebee.searchmovie.cache.di

import com.sangmeebee.searchmovie.cache.datasource.MovieBookmarkLocalDataSourceImpl
import com.sangmeebee.searchmovie.data.datasource.local.MovieBookmarkLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DatasourceModule {

    @Singleton
    @Binds
    abstract fun bindMovieBookmarkLocalDataSource(impl: MovieBookmarkLocalDataSourceImpl): MovieBookmarkLocalDataSource

}