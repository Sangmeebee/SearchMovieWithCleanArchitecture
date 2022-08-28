package com.sangmeebee.searchmovie.domain.usecase

import com.sangmeebee.searchmovie.domain.repository.MovieRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetMovieUseCaseTest {

    private lateinit var getMovieUseCase: GetMovieUseCase
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        movieRepository = mockk(relaxed = true)
        getMovieUseCase = GetMovieUseCase(movieRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `영화 정보를 불러온다`() = runTest {

        //when
        getMovieUseCase("")

        //then
        coVerify { movieRepository.getMovies("") }
    }
}