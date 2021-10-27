package com.rustamsadykov.firstapp.ui.emailconfirmation

import androidx.fragment.app.viewModels
import com.rustamsadykov.firstapp.R
import com.rustamsadykov.firstapp.ui.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rustamsadykov.firstapp.databinding.FragmentEmailConfimationBinding

class EmailConfirmationFragment : BaseFragment(R.layout.fragment_email_confimation) {

    private val viewBinding by viewBinding(FragmentEmailConfimationBinding::bind)

    private val viewModel: EmailConfirmationViewModel by viewModels()
}