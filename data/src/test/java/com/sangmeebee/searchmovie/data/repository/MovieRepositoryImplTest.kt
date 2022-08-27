package com.sangmeebee.searchmovie.data.repository

import com.sangmeebee.searchmovie.data.datasource.remote.MovieRemoteDataSource
import com.sangmeebee.searchmovie.domain.repository.MovieRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MovieRepositoryImplTest {


    private lateinit var movieRepository: MovieRepository
    private lateinit var movieRemoteDataSource: MovieRemoteDataSource

    @Before
    fun setUp() {
        movieRemoteDataSource = mockk(relaxed = true)
        movieRepository = MovieRepositoryImpl(movieRemoteDataSource)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `해당_검색어의_영화_리스트를_불러온다`() = runTest {
        //when
        movieRemoteDataSource.getMovies("김상민")

        //then
        coVerify { movieRepository.getMovies("김상민") }
    }
}