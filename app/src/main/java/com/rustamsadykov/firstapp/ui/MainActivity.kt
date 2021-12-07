package com.rustamsadykov.firstapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rustamsadykov.firstapp.R
import com.rustamsadykov.firstapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    private val viewBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        subscribeToAuthorizationStatus()
    }

    private fun subscribeToAuthorizationStatus() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isAuthorizedFlow().collect(::showSuitableNavigationFlow)
            }
        }
    }

    // This method have to be idempotent. Do not override restored backstack.
    private fun showSuitableNavigationFlow(isAuthorized: Boolean) {
        val navController = findNavController(R.id.mainActivityNavigationHost)
        when (isAuthorized) {
            true -> {
                if (navController.backQueue.any { it.destination.id == R.id.registered_nav_graph}) {
                    return
                }
                navController.navigate(R.id.action_registeredNavGraph)
            }
            false -> {
                if (navController.backQueue.any { it.destination.id == R.id.guest_nav_graph}) {
                    return
                }
                navController.navigate(R.id.action_guestNavGraph)
            }
        }
    }

}