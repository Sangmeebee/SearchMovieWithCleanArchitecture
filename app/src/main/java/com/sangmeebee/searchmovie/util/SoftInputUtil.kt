package com.sangmeebee.searchmovie.util

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager

class SoftInputUtil(context: Context) {
    private val imm: InputMethodManager =
        context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

    fun hideKeyboard(view: View?) {
        view?.clearFocus()
        imm.hideSoftInputFromWindow(view?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}