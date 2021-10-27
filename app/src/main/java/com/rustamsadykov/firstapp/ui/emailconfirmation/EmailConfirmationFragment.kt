package com.rustamsadykov.firstapp.ui.emailconfirmation

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rustamsadykov.firstapp.R
import com.rustamsadykov.firstapp.databinding.FragmentEmailConfimationBinding
import com.rustamsadykov.firstapp.ui.base.BaseFragment

class EmailConfirmationFragment : BaseFragment(R.layout.fragment_email_confimation) {

    private val viewBinding by viewBinding(FragmentEmailConfimationBinding::bind)

    private val viewModel: EmailConfirmationViewModel by viewModels()
}