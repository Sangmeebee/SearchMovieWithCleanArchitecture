package com.sangmeebee.searchmovie.ui.detail_movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sangmeebee.searchmovie.databinding.FragmentDetailMovieBinding
import com.sangmeebee.searchmovie.ui.base.BaseFragment

class DetailMovieFragment :
    BaseFragment<FragmentDetailMovieBinding>(FragmentDetailMovieBinding::inflate) {

    private val args: DetailMovieFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (binding.webview.canGoBack()) {
                binding.webview.goBack()
            } else {
                findNavController().navigateUp()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.linkArg?.let {
            binding.webview.apply {
                settings.javaScriptEnabled = true
                webViewClient = DetailMovieWebViewClient()
                loadUrl(it)
            }
        }
    }
}