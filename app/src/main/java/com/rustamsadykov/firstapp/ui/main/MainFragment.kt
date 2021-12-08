package com.rustamsadykov.firstapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rustamsadykov.firstapp.R
import com.rustamsadykov.firstapp.databinding.FragmentMainBinding
import com.rustamsadykov.firstapp.ui.base.BaseFragment
import dev.chrisbanes.insetter.applyInsetter

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val viewBinding by viewBinding(FragmentMainBinding::bind)

    private val viewModel: MainFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            val navController =
                (childFragmentManager.findFragmentById(R.id.mainFragmentNavigationHost)
                        as NavHostFragment).navController

            bottomNavigationView.setupWithNavController(navController)
            bottomNavigationView.applyInsetter {
                type(navigationBars = true) { margin() }
            }
        }
    }

}