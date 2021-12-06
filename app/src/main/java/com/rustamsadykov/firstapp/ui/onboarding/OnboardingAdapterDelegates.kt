package com.rustamsadykov.firstapp.ui.onboarding

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.rustamsadykov.firstapp.databinding.ItemOnboardingTextBinding

fun onboardingTextAdapterDelegate() =
    adapterDelegateViewBinding<String, CharSequence, ItemOnboardingTextBinding>(
        viewBinding = { layoutInflater, parent ->
            ItemOnboardingTextBinding.inflate(layoutInflater, parent, false)
        },
        block = {
            bind {
                binding.onboardingTextView.text = item
            }
        }
    )
