package com.sangmeebee.searchmovie.ui.detail_movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sangmeebee.searchmovie.databinding.FragmentDetailMovieBinding
import com.sangmeebee.searchmovie.ui.base.BaseFragment
import com.sangmeebee.searchmovie.ui.detail_movie.DetailMovieWebViewClient.Companion.BASE_URL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment :
    BaseFragment<FragmentDetailMovieBinding>(FragmentDetailMovieBinding::inflate) {

    private val args: DetailMovieFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnBackPressedDispatcher()
    }

    private fun setOnBackPressedDispatcher() {
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

        binding.webview.apply {
            settings.javaScriptEnabled = true
            webViewClient = DetailMovieWebViewClient()
            args.linkArg?.let {
                loadUrl(it)
            } ?: run {
                loadUrl(BASE_URL)
            }
        }
    }
}