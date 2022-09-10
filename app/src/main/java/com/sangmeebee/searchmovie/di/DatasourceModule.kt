package com.sangmeebee.searchmovie.di

import com.sangmeebee.searchmovie.data.datasource.local.MovieBookmarkLocalDataSource
import com.sangmeebee.searchmovie.data.datasource.local.MovieBookmarkLocalDataSourceImpl
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