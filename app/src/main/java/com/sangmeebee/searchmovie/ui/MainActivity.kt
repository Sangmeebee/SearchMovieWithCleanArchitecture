package com.sangmeebee.searchmovie.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sangmeebee.searchmovie.R
import com.sangmeebee.searchmovie.databinding.ActivityMainBinding
import com.sangmeebee.searchmovie.domain.util.EmptyQueryException
import com.sangmeebee.searchmovie.model.UIState
import com.sangmeebee.searchmovie.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private val movieAdapter: MovieAdapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            this.lifecycleOwner = this@MainActivity
            this.viewModel = mainViewModel
        }
        setContentView(binding.root)

        setRecyclerView()
        observeUIState()
        observeQuery()
    }

    private fun observeQuery() = repeatOnStarted {
        mainViewModel.query.collect { query ->
            query?.let {
                binding.etQuery.apply {
                    setText(it)
                    setSelection(it.length)
                }
            }
        }
    }

    private fun observeUIState() = repeatOnStarted {
        mainViewModel.uiState.collect { uiState ->
            when (uiState) {
                is UIState.Empty -> {
                    binding.srlLoading.isRefreshing = false
                    movieAdapter.submitList(emptyList())
                }
                is UIState.Loading -> {
                    binding.srlLoading.isRefreshing = true
                }
                is UIState.Success -> {
                    binding.srlLoading.isRefreshing = false
                    movieAdapter.submitList(uiState.data)
                }
                is UIState.Error -> {
                    binding.srlLoading.isRefreshing = false
                    when (uiState.throwable) {
                        is EmptyQueryException -> showToast(resources.getString(R.string.movie_list_empty_query))
                        else -> showToast(uiState.throwable.message)
                    }
                }
            }
        }
    }

    private fun setRecyclerView() {
        binding.rvMovieList.adapter = movieAdapter
    }

    private fun showToast(message: String?) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}