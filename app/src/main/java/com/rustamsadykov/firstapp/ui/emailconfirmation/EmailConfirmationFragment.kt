package com.rustamsadykov.firstapp.ui.emailconfirmation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rustamsadykov.firstapp.R
import com.rustamsadykov.firstapp.databinding.FragmentEmailConfimationBinding
import com.rustamsadykov.firstapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter

@AndroidEntryPoint
class EmailConfirmationFragment : BaseFragment(R.layout.fragment_email_confimation) {

    private val viewBinding by viewBinding(FragmentEmailConfimationBinding::bind)

    private val viewModel: EmailConfirmationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {

            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            confirmButton.setOnClickListener {
                viewModel.signIn("", "")
            }

            confirmButton.applyInsetter {
                type(navigationBars = true) { margin() }
            }

            backButton.applyInsetter {
                type(statusBars = true) { margin() }
            }
        }
    }

}