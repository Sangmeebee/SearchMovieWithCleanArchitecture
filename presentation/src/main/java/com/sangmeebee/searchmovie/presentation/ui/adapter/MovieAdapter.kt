package com.sangmeebee.searchmovie.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sangmeebee.searchmovie.presentation.databinding.ItemMovieBinding
import com.sangmeebee.searchmovie.presentation.model.MovieModel

class MovieAdapter : PagingDataAdapter<MovieModel, MovieAdapter.ViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding).apply {
            binding.ivBookmark.setOnClickListener {
                snapshot()[bindingAdapterPosition]?.let { movie ->
                    movie.bookmark()
                    movie.isBookmarked = !movie.isBookmarked
                }
                notifyItemChanged(bindingAdapterPosition)
            }
        }
    }


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
        oldItem.link == newItem.link


    override fun areContentsTheSame(
        oldItem: MovieModel,
        newItem: MovieModel,
    ): Boolean = oldItem == newItem
}