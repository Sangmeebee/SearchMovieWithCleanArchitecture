package com.sangmeebee.searchmovie.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sangmeebee.searchmovie.R
import com.sangmeebee.searchmovie.databinding.ItemMovieBinding
import com.sangmeebee.searchmovie.model.MovieUIState

class MovieAdapter : PagingDataAdapter<MovieUIState, MovieAdapter.ViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            .let { view -> ViewHolder(view) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemMovieBinding.bind(view)
        fun bind(movie: MovieUIState?) {
            if (movie != null) {
                binding.movie = movie
            }
            binding.executePendingBindings()
        }
    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<MovieUIState>() {
    override fun areItemsTheSame(
        oldItem: MovieUIState,
        newItem: MovieUIState,
    ): Boolean = oldItem.hashCode() == newItem.hashCode()


    override fun areContentsTheSame(
        oldItem: MovieUIState,
        newItem: MovieUIState,
    ): Boolean = oldItem == newItem
}