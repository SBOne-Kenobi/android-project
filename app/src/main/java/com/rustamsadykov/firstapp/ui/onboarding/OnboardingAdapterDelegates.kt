package com.rustamsadykov.firstapp.ui.onboarding

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.rustamsadykov.firstapp.databinding.ItemOnboardingTextBinding
import com.rustamsadykov.firstapp.utils.extentions.dpToPx

fun onboardingTextAdapterDelegate() =
    adapterDelegateViewBinding<String, CharSequence, ItemOnboardingTextBinding>(
        viewBinding = { layoutInflater, parent ->
            ItemOnboardingTextBinding.inflate(layoutInflater, parent, false)
        },
        block = {
            bind {
                binding.onboardingTextView.apply {
                    text = item
                    val pad = dpToPx(48.0f).toInt()
                    setPadding(pad, 0, pad, 0)
                }
            }
        }
    )
