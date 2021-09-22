package com.rustamsadykov.firstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rustamsadykov.firstapp.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val viewBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { renderViewState(it) }
            }
        }
    }

    private fun renderViewState(viewState: MainViewModel.ViewState) {
        when (viewState) {
            is MainViewModel.ViewState.Loading -> {
                viewBinding.usersRecyclerView.isVisible = false
                viewBinding.progressBar.isVisible = true
            }
            is MainViewModel.ViewState.Data -> {
                viewBinding.usersRecyclerView.isVisible = true
                (viewBinding.usersRecyclerView.adapter as UserAdapter).apply {
                    userList = viewState.userList
                    notifyDataSetChanged()
                }
                viewBinding.progressBar.isVisible = false
            }
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = viewBinding.usersRecyclerView

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        getDrawable(R.drawable.divider_user)?.let { itemDecoration.setDrawable(it) }
        recyclerView.addItemDecoration(itemDecoration)

        recyclerView.adapter = UserAdapter()
    }

}