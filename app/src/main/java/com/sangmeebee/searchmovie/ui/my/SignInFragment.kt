package com.sangmeebee.searchmovie.ui.my

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.user.UserApiClient
import com.sangmeebee.searchmovie.databinding.FragmentSigninBinding
import com.sangmeebee.searchmovie.ui.UserViewModel
import com.sangmeebee.searchmovie.ui.base.BaseFragment
import com.sangmeebee.searchmovie.util.login
import com.sangmeebee.searchmovie.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class SignInFragment :
    BaseFragment<FragmentSigninBinding>(FragmentSigninBinding::inflate) {

    private lateinit var savedStateHandle: SavedStateHandle
    private val userViewModel by activityViewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeError()
        observeDoLogin()
        observeIsLoading()
    }

    override fun FragmentSigninBinding.setBinding() {
        lifecycleOwner = viewLifecycleOwner
        viewModel = userViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
        savedStateHandle[SIGN_IN_SUCCESSFUL] = false
        setSwipeRefreshLayout()
    }

    private fun setSwipeRefreshLayout() {
        binding.srlLoading.isEnabled = false
    }


    private fun observeDoLogin() = repeatOnStarted {
        Log.d("Sangmeebee", "test1")
        userViewModel.signInUiState.map { it.doLogin }.distinctUntilChanged()
            .collectLatest { doLogin ->
                Log.d("Sangmeebee", "test2")
                if (doLogin) {
                    UserApiClient.login(requireContext())
                        .onSuccess {
                            userViewModel.fetchUser()
                            savedStateHandle[SIGN_IN_SUCCESSFUL] = true
                            findNavController().popBackStack()
                        }
                        .onFailure { userViewModel.signInShowErrorMessage(it) }
                }
            }
    }

    private fun observeError() = repeatOnStarted {
        Log.d("Sangmeebee", "test3")
        userViewModel.signInUiState.map { it.error }.distinctUntilChanged().collectLatest { error ->
            Log.d("Sangmeebee", "test4")
            error?.let {
                showToast(error.message)
                userViewModel.signInErrorMessageShown()
            }
        }
    }

    private fun observeIsLoading() = repeatOnStarted {
        userViewModel.signInUiState.map { it.doLogin }.distinctUntilChanged().collectLatest {
            binding.srlLoading.isRefreshing = it
        }
    }

    companion object {
        const val SIGN_IN_SUCCESSFUL: String = "SIGN_IN_SUCCESSFUL"
    }
}