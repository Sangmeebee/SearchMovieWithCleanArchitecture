package com.sangmeebee.searchmovie.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

open class BaseFragment<T : ViewDataBinding>(
    @LayoutRes private val contentLayoutId: Int,
) : Fragment() {

    private var _binding: T? = null
    protected val binding: T by lazy { _binding!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate<T>(inflater, contentLayoutId, container, false).apply {
            setBinding()
        }
        return binding.root
    }

    open fun T.setBinding() {}

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}