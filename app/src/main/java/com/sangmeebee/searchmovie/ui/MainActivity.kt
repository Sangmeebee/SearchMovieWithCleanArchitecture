package com.sangmeebee.searchmovie.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.sangmeebee.searchmovie.R
import com.sangmeebee.searchmovie.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        setBottomNavigation(navController)
        setOnBackPressedDispatcher()
    }

    private fun setBottomNavigation(navController: NavController) {
        binding.bottomNav.setupWithNavController(navController)
    }

    private fun setOnBackPressedDispatcher() =
        onBackPressedDispatcher.addCallback(this) {
            if (!findNavController(R.id.nav_host_fragment).popBackStack()) {
                finish()
            }
        }
}