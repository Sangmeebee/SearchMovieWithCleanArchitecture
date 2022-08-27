package com.sangmeebee.searchmovie.data.di

import com.sangmeebee.searchmovie.data.repository.MovieRepositoryImpl
import com.sangmeebee.searchmovie.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

}