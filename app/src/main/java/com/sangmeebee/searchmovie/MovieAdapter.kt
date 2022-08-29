package com.sangmeebee.searchmovie

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter : ListAdapter<MovieEntity, MovieAdapter.ViewHolder> {


    class ViewHolder(private val binding: ItemEmployBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EmployNotice) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

}