package com.sangmeebee.searchmovie.cache.di

import com.sangmeebee.searchmovie.cache.datasource.MovieBookmarkLocalDataSourceImpl
import com.sangmeebee.searchmovie.cache.datasource.UserInfoLocalDataSourceImpl
import com.sangmeebee.searchmovie.cache.datasource.UserTokenLocalDataSourceImpl
import com.sangmeebee.searchmovie.data.datasource.local.MovieBookmarkLocalDataSource
import com.sangmeebee.searchmovie.data.datasource.local.UserInfoLocalDataSource
import com.sangmeebee.searchmovie.data.datasource.local.UserTokenLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindMovieBookmarkLocalDataSource(impl: MovieBookmarkLocalDataSourceImpl): MovieBookmarkLocalDataSource

    @Singleton
    @Binds
    abstract fun bindUserTokenLocalDataSource(impl: UserTokenLocalDataSourceImpl): UserTokenLocalDataSource

    @Singleton
    @Binds
    abstract fun bindUserInfoLocalDataSource(impl: UserInfoLocalDataSourceImpl): UserInfoLocalDataSource
}