package com.sangmeebee.searchmovie.util

import android.view.inputmethod.EditorInfo
import android.widget.EditText
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

@BindingAdapter("onActionDone")
fun EditText.setOnActionDone(onActionDone: (String) -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        return@setOnEditorActionListener when (actionId) {
            EditorInfo.IME_ACTION_DONE -> {
                onActionDone(text.toString())
                true
            }
            else -> false

        }
    }
}
