package com.sangmeebee.searchmovie.ui.my

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.user.UserApiClient
import com.sangmeebee.searchmovie.databinding.FragmentSigninBinding
import com.sangmeebee.searchmovie.ui.base.BaseFragment
import com.sangmeebee.searchmovie.util.login
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment :
    BaseFragment<FragmentSigninBinding>(FragmentSigninBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setKakaoLoginButtonClickListener()
    }

    private fun setKakaoLoginButtonClickListener() {
        binding.btnKakaoLogin.setOnClickListener {
            lifecycleScope.launch {
                UserApiClient.login(requireContext())
                    .onSuccess { Log.d("SearchMovie", it.toString() + "성공성공") }
                    .onFailure { Log.d("SearchMovie", it.toString() + "실패실패") }
            }
        }
    }
}