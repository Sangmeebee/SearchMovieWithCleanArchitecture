package com.sangmeebee.searchmovie.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    val title: String,
    val subtitle: String? = null,
    @PrimaryKey
    val link: String,
    val imageUrl: String?,
    val releaseDate: String,
    val director: String,
    val actor: String,
    val userRating: String,
)