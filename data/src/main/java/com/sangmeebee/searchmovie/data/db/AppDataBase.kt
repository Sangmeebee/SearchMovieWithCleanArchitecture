package com.sangmeebee.searchmovie.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sangmeebee.searchmovie.domain.model.Movie

@Database(entities = [Movie::class, RemoteKeys::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}