package com.sangmeebee.searchmovie.presentation.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("imageUrl")
fun ImageView.setImageByUrl(url: String) {
    load(url) {
        crossfade(true)
    }
}

@BindingAdapter("htmlText")
fun TextView.setHtmlText(text: String) {
    setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY))
}
