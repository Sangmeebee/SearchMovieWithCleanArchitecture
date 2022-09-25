package com.sangmeebee.searchmovie.ui.detail_movie

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class DetailMovieWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        val uri = request.url
        return if (uri != null) {
            false
        } else {
            view.loadUrl(BASE_URL)
            true
        }
    }

    companion object {
        const val BASE_URL = "https://www.naver.com"
    }
}
