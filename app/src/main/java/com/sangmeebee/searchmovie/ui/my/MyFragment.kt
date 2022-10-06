package com.sangmeebee.searchmovie.ui.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sangmeebee.searchmovie.R
import com.sangmeebee.searchmovie.databinding.FragmentMyBinding
import com.sangmeebee.searchmovie.ui.UserViewModel
import com.sangmeebee.searchmovie.ui.base.BaseFragment
import com.sangmeebee.searchmovie.util.repeatOnStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MyFragment : BaseFragment<FragmentMyBinding>(FragmentMyBinding::inflate) {

    private val userViewModel by activityViewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeUser()
        observeError()
        observeSignIn()
        observeIsLoading()
    }

    override fun FragmentMyBinding.setBinding() {
        lifecycleOwner = viewLifecycleOwner
        viewModel = userViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSwipeRefreshLayout()
    }

    private fun setSwipeRefreshLayout() {
        binding.srlLoading.isEnabled = false
    }

    private fun observeSignIn() = repeatOnStarted {
        val currentBackStackEntry = findNavController().currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle
        savedStateHandle.getStateFlow(SignInFragment.SIGN_IN_SUCCESSFUL, true)
            .collectLatest { success ->
                if (!success) {
                    findNavController().popBackStack()
                }
            }
    }

    private fun observeIsLoading() = repeatOnStarted {
        userViewModel.myUiState.map { it.isLoading }.distinctUntilChanged().collectLatest {
            binding.srlLoading.isRefreshing = it
        }
    }

    private fun observeUser() = lifecycleScope.launch {
        userViewModel.myUiState.map { it.user }.distinctUntilChanged().collectLatest { user ->
            if (user == null) {
                findNavController().navigate(R.id.signInFragment)
            }
        }
    }

    private fun observeError() = lifecycleScope.launch {
        userViewModel.myUiState.map { it.error }.distinctUntilChanged().collectLatest { error ->
            if (error != null) {
                showToast(error.message)
                userViewModel.myErrorMessageShown()
            }
        }
    }
}