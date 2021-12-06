package com.rustamsadykov.firstapp.ui.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rustamsadykov.firstapp.R
import com.rustamsadykov.firstapp.databinding.FragmentSignInBinding
import com.rustamsadykov.firstapp.ui.base.BaseFragment

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    private val viewBinding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.apply {
            signInButton.setOnClickListener {
                viewModel.signIn()
            }

            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }

    }
}