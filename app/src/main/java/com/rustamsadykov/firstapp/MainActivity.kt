package com.rustamsadykov.firstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rustamsadykov.firstapp.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()

    private val viewBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = setupRecyclerView()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    when (state) {
                        is MainViewModel.ViewState.Data -> {
                            adapter.userList = state.userList
                            adapter.notifyDataSetChanged()
                            viewBinding.usersRecyclerView.isVisible = true
                            viewBinding.progressBar.isVisible = false
                        }
                        else -> {
                            viewBinding.usersRecyclerView.isVisible = false
                            viewBinding.progressBar.isVisible = true
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(): UserAdapter {
        val recyclerView = findViewById<RecyclerView>(R.id.usersRecyclerView)

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        getDrawable(R.drawable.divider_user)?.let { itemDecoration.setDrawable(it) }
        recyclerView.addItemDecoration(itemDecoration)

        val adapter = UserAdapter()
        recyclerView.adapter = adapter

        return adapter
    }

}