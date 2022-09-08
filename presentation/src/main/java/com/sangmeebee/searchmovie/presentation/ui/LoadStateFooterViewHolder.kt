package com.sangmeebee.searchmovie.presentation.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.sangmeebee.searchmovie.presentation.R
import com.sangmeebee.searchmovie.presentation.databinding.ItemLoadStateFooterBinding

class LoadStateFooterViewHolder(
    private val binding: ItemLoadStateFooterBinding,
    retry: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.llReload.setOnClickListener {
            retry.invoke()
        }
    }

    fun bind(loadState: LoadState) {
        Log.d("Sangmeebee", "hihi")
        binding.pbLoad.isVisible = loadState is LoadState.Loading
        binding.llReload.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateFooterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_load_state_footer, parent, false)
            val binding = ItemLoadStateFooterBinding.bind(view)
            return LoadStateFooterViewHolder(binding, retry)
        }
    }
}