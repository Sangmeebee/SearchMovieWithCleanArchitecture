package com.sangmeebee.searchmovie.cache.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.sangmeebee.searchmovie.cache.UserTokenPref
import com.sangmeebee.searchmovie.cache.datastore.UserTokenPrefSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private const val USER_TOKEN_DATA_STORE_FILE_NAME = "user_token_pref.pb"

    @Singleton
    @Provides
    fun provideLoginTypeProtoDataStore(@ApplicationContext appContext: Context): DataStore<UserTokenPref> {
        return DataStoreFactory.create(
            serializer = UserTokenPrefSerializer,
            produceFile = { appContext.dataStoreFile(USER_TOKEN_DATA_STORE_FILE_NAME) },
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }
}