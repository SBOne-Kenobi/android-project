package com.rustamsadykov.firstapp.ui.signup

import androidx.fragment.app.viewModels
import com.rustamsadykov.firstapp.R
import com.rustamsadykov.firstapp.ui.base.BaseFragment
import com.rustamsadykov.firstapp.databinding.FragmentSignUpBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private val viewBinding by viewBinding(FragmentSignUpBinding::bind)

    private val viewModel: SignUpViewModel by viewModels()
}