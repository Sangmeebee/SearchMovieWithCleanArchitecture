package com.sangmeebee.searchmovie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.sangmeebee.searchmovie.databinding.ItemMovieBinding
import com.sangmeebee.searchmovie.model.MovieModel

class SearchMovieAdapter(
    private val bookmark: (MovieModel, Boolean) -> Unit,
    private val navigateToDetailFragment: (String) -> Unit,
) :
    PagingDataAdapter<MovieModel, MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = MovieViewHolder(binding)
        binding.ivBookmark.setOnClickListener {
            getItem(viewHolder.bindingAdapterPosition)?.let { movie -> bookmark(movie, movie.isBookmarked) }
        }
        binding.clContainer.setOnClickListener {
            getItem(viewHolder.bindingAdapterPosition)?.let { movie ->
                navigateToDetailFragment(movie.link)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(getItem(position))
}

