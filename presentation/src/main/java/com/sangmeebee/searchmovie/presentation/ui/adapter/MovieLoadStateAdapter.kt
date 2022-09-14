package com.sangmeebee.searchmovie.presentation.ui.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class MovieLoadStateAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<LoadStateFooterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): LoadStateFooterViewHolder {
        return LoadStateFooterViewHolder.create(parent, retry)
    }


    override fun onBindViewHolder(holder: LoadStateFooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}