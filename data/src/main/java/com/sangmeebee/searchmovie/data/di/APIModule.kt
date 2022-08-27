package com.sangmeebee.searchmovie.data.di

import com.sangmeebee.searchmovie.data.service.MovieAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(Singleton::class)
@Module
object APIModule {

    @Singleton
    @Provides
    internal fun provideMovieAPI(retrofit: Retrofit): MovieAPI =
        retrofit.create(MovieAPI::class.java)

}