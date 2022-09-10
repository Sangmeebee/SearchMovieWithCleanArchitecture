package com.sangmeebee.searchmovie.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sangmeebee.searchmovie.data.model.mapper.DataToDomainMapper

@Entity(tableName = "movie_bookmark")
data class MovieBookmarkEntity(
    @PrimaryKey
    val movieId: String,
) : DataToDomainMapper<String> {
    override fun toDomain(): String = movieId
}