package com.rustamsadykov.firstapp.ui.signin

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.TranslateAnimation
import androidx.activity.addCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rustamsadykov.firstapp.R
import com.rustamsadykov.firstapp.databinding.FragmentSignInBinding
import com.rustamsadykov.firstapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter

@AndroidEntryPoint
class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    private val viewBinding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback (this) {
            onBackButtonPressed()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            signInButton.setOnClickListener {
                viewModel.signIn(
                    email = emailEditText.text?.toString() ?: "",
                    password = passwordEditText.text?.toString() ?: ""
                )
            }
            backButton.setOnClickListener {
                onBackButtonPressed()
            }
            backButton.applyInsetter {
                type(statusBars = true) { margin() }
            }
            signInButton.applyInsetter {
                type(navigationBars = true) { margin() }
            }
        }

        subscribeToFormFields()
        setupAnimations()
    }

    private fun setupAnimations() {
        viewBinding.apply {
            mknLogoImageView.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        mknLogoImageView.viewTreeObserver.removeOnPreDrawListener(this)
                        val startYPos = mknLogoImageView.y + mknLogoImageView.measuredHeight
                        val fallingAnimation = TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0.0f,
                            Animation.RELATIVE_TO_SELF, 0.0f,
                            Animation.ABSOLUTE, -startYPos,
                            Animation.RELATIVE_TO_SELF, 0.0f
                        ).apply {
                            duration = 2000
                            fillAfter = true
                            interpolator = BounceInterpolator()
                        }
                        mknLogoImageView.startAnimation(fallingAnimation)
                        return true
                    }
                }
            )
        }
    }

    private fun onBackButtonPressed() {
        viewBinding.apply {
            if (emailEditText.text.isNullOrBlank() && passwordEditText.text.isNullOrBlank()) {
                findNavController().popBackStack()
                return
            }
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.common_back_alert_dialog_text)
                .setNegativeButton(
                    R.string.common_back_alert_dialog_cancel_button_text
                ) { dialog, _ -> dialog.dismiss() }
                .setPositiveButton(
                    R.string.common_back_alert_dialog_apply_button_text
                ) { _, _ -> findNavController().popBackStack() }
                .show()
        }
    }

    private fun subscribeToFormFields() {
        viewBinding.apply {
            decideSignInButtonEnabledState(
                email = emailEditText.text?.toString(),
                password = passwordEditText.text?.toString()
            )

            emailEditText.doAfterTextChanged { email ->
                decideSignInButtonEnabledState(
                    email = email?.toString(),
                    password = passwordEditText.text?.toString()
                )
            }

            passwordEditText.doAfterTextChanged { password ->
                decideSignInButtonEnabledState(
                    email = emailEditText.text?.toString(),
                    password = password?.toString()
                )
            }

        }
    }

    private fun decideSignInButtonEnabledState(email: String?, password: String?) {
        viewBinding.signInButton.isEnabled = !email.isNullOrBlank() && !password.isNullOrBlank()
    }
}