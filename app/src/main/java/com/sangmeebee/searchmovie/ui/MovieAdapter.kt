package com.sangmeebee.searchmovie.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sangmeebee.searchmovie.R
import com.sangmeebee.searchmovie.databinding.ItemMovieBinding
import com.sangmeebee.searchmovie.model.MovieInfoModel

class MovieAdapter : ListAdapter<MovieInfoModel, MovieAdapter.ViewHolder>(MovieInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            .let { view -> ViewHolder(view) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemMovieBinding.bind(view)
        fun bind(movieInfo: MovieInfoModel) {
            binding.movieInfo = movieInfo
            binding.executePendingBindings()
        }
    }
}

class MovieInfoDiffCallback : DiffUtil.ItemCallback<MovieInfoModel>() {
    override fun areItemsTheSame(
        oldItem: MovieInfoModel,
        newItem: MovieInfoModel,
    ): Boolean = oldItem.hashCode() == newItem.hashCode()


    override fun areContentsTheSame(
        oldItem: MovieInfoModel,
        newItem: MovieInfoModel,
    ): Boolean = oldItem == newItem
}