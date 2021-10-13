package com.rustamsadykov.firstapp.ui.signin

import androidx.fragment.app.viewModels
import com.rustamsadykov.firstapp.R
import com.rustamsadykov.firstapp.ui.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rustamsadykov.firstapp.databinding.FragmentSignInBinding

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    private val viewBinding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel: SignInViewModel by viewModels()
}