package com.sangmeebee.searchmovie.data.datasource

import com.google.common.truth.Truth.assertThat
import com.sangmeebee.searchmovie.data.MainDispatcherRule
import com.sangmeebee.searchmovie.data.datasource.remote.MovieRemoteDataSource
import com.sangmeebee.searchmovie.data.datasource.remote.MovieRemoteDataSourceImpl
import com.sangmeebee.searchmovie.data.service.MovieAPI
import com.sangmeebee.searchmovie.domain.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class MovieRemoteDataSourceImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var movieAPI: MovieAPI
    private lateinit var movieRemoteDataSource: MovieRemoteDataSource


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        movieAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)

        movieRemoteDataSource = MovieRemoteDataSourceImpl(
            movieAPI = movieAPI,
            ioDispatcher = mainDispatcherRule.testDispatcher
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun httpException이_발생하면_실패를_반환한다() = runTest {
        //given
        val response = MockResponse().setResponseCode(400)
        mockWebServer.enqueue(response)

        //when
        val actual: Result<Movie> = movieRemoteDataSource.getMovies("")
        advanceUntilIdle()

        //then
        assertThat(actual.isFailure).isTrue()

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `httpStatus가_200이면_성공을_반환한다`() = runTest {
        //given
        val response = MockResponse().setResponseCode(200)
            .setBody(File("src/test/resources/repositories/empty.json").readText())
        mockWebServer.enqueue(response)

        //when
        val actual: Result<Movie> = movieRemoteDataSource.getMovies("a")
        advanceUntilIdle()
        print(actual)

        //then
        assertThat(actual.isSuccess).isTrue()
    }
}