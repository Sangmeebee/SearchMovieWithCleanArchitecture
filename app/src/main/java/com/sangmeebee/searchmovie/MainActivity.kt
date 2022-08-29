package com.sangmeebee.searchmovie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sangmeebee.searchmovie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val movieAdapter: MovieAdapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setRecyclerView()
    }

    private fun setRecyclerView() {
        binding.rvMovieList.adapter = movieAdapter
    }
}