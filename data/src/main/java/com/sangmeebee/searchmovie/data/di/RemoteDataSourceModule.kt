package com.sangmeebee.searchmovie.data.di

import com.sangmeebee.searchmovie.data.datasource.remote.MovieRemoteDataSource
import com.sangmeebee.searchmovie.data.datasource.remote.MovieRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RemoteDataSourceModule {

    @Singleton
    @Binds
    abstract fun bindMovieRemoteDataSource(impl: MovieRemoteDataSourceImpl): MovieRemoteDataSource

}