package com.sangmeebee.searchmovie.cache.model

import androidx.room.Embedded
import androidx.room.Relation

internal data class UserWithBookmarkedMoviesPref(
    @Embedded val user: UserPref,
    @Relation(
        parentColumn = "user_token",
        entityColumn = "user_owner_token"
    )
    val bookmarkedMovies: List<BookmarkedMoviePref>,
)