package com.rustamsadykov.firstapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import com.rustamsadykov.firstapp.databinding.FragmentUserListBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class UserListFragment : Fragment(R.layout.fragment_user_list) {

    private val viewModel: UserListViewModel by viewModels()

    private val viewBinding by viewBinding(FragmentUserListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewState()
    }

    private fun observeViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect(::renderViewState)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderViewState(viewState: UserListViewModel.ViewState) {
        when (viewState) {
            is UserListViewModel.ViewState.Loading -> {
                viewBinding.usersRecyclerView.isVisible = false
                viewBinding.progressBar.isVisible = true
            }
            is UserListViewModel.ViewState.Data -> {
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

        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        context?.let { getDrawable(it, R.drawable.divider_user)?.let { itemDecoration.setDrawable(it) } }
        recyclerView.addItemDecoration(itemDecoration)

        recyclerView.adapter = UserAdapter()
    }

}