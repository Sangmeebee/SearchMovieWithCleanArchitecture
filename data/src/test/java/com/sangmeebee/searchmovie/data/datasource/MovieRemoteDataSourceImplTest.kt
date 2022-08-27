package com.sangmeebee.searchmovie.data.datasource

import com.sangmeebee.searchmovie.data.MainDispatcherRule
import com.sangmeebee.searchmovie.data.service.MovieAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRemoteDataSourceImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var movieAPI: MovieAPI


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        movieAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)

    }

    @Test
    fun httpException_400_이면_실패를_반환한다() = runTest {
        //given


        //when


        //then

    }

    @Test
    fun `httpStatus가 200이면 성공을 반환한다`() {
        //given


        //when


        //then

    }
}