package com.sangmeebee.searchmovie.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sangmeebee.searchmovie.databinding.ItemMovieBinding
import com.sangmeebee.searchmovie.model.MovieModel

class MovieAdapter : PagingDataAdapter<MovieModel, MovieAdapter.ViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieModel?) {
            if (movie != null) {
                binding.movie = movie
            }
        }
    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<MovieModel>() {
    override fun areItemsTheSame(
        oldItem: MovieModel,
        newItem: MovieModel,
    ): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(
        oldItem: MovieModel,
        newItem: MovieModel,
    ): Boolean = oldItem == newItem
}