package com.rustamsadykov.firstapp.ui.main

import androidx.fragment.app.viewModels
import com.rustamsadykov.firstapp.R
import com.rustamsadykov.firstapp.ui.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rustamsadykov.firstapp.databinding.FragmentMainBinding

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val viewBinding by viewBinding(FragmentMainBinding::bind)

    private val viewModel: MainFragmentViewModel by viewModels()
}