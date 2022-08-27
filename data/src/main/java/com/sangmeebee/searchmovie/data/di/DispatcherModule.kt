package com.sangmeebee.searchmovie.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(Singleton::class)
@Module
internal object DispatcherModule {

    @Singleton
    @IoDispatcher
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher