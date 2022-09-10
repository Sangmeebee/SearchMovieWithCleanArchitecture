package com.sangmeebee.searchmovie.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sangmeebee.searchmovie.data.model.MovieBookmarkEntity

@Database(entities = [MovieBookmarkEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun movieBookmarkDao(): MovieBookmarkDao
}