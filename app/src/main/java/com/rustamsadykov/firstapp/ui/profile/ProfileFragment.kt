package com.rustamsadykov.firstapp.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.rustamsadykov.firstapp.R
import com.rustamsadykov.firstapp.databinding.FragmentProfileBinding
import com.rustamsadykov.firstapp.domain.User
import com.rustamsadykov.firstapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private val viewBinding by viewBinding(FragmentProfileBinding::bind)

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToEvents()
        viewBinding.logoutButton.applyInsetter {
            type(statusBars = true) { margin() }
        }
        viewBinding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
        viewBinding.userNameTextView.applyInsetter {
            type(statusBars = true) { margin() }
        }
        fillProfile()
    }

    private fun subscribeToEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventsFlow().collect { event ->
                    when (event) {
                        is ProfileViewModel.Event.LogoutError -> {
                            Toast
                                .makeText(
                                    requireContext(),
                                    R.string.common_general_error_text,
                                    Toast.LENGTH_LONG
                                )
                                .show()
                        }
                        is ProfileViewModel.Event.LoadedProfile -> {
                            fillProfile()
                        }
                    }
                }
            }
        }
    }

    private fun fillProfile() {
        viewBinding.apply {
            viewModel.user?.apply {
                userNameTextView.text = userName
                firstNameTextView.text = firstName
                secondNameTextView.text = secondName
                Glide.with(avatarImageView)
                    .load(avatarUrl)
                    .circleCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(avatarImageView)
            }
        }
    }

}