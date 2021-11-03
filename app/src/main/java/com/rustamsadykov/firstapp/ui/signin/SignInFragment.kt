package com.rustamsadykov.firstapp.ui.signin

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rustamsadykov.firstapp.R
import com.rustamsadykov.firstapp.databinding.FragmentSignInBinding
import com.rustamsadykov.firstapp.ui.base.BaseFragment

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

            decideSignInButtonEnabledState(
                email = emailEditText.text?.toString(),
                password = passwordEditText.text?.toString()
            )

            signInButton.setOnClickListener {
                viewModel.signIn(
                    email = emailEditText.text?.toString() ?: "",
                    password = passwordEditText.text?.toString() ?: ""
                )
            }

            backButton.setOnClickListener {
                onBackButtonPressed()
            }

        }

        subscribeToFormFields()
    }

    private fun onBackButtonPressed() {
        viewBinding.apply {
            if (emailEditText.text.isNullOrBlank() && passwordEditText.text.isNullOrBlank()) {
                findNavController().popBackStack()
                return
            }
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.sign_in_back_alert_dialog_text)
                .setNegativeButton(
                    R.string.sign_in_back_alert_dialog_cancel_button_text
                ) { dialog, _ -> dialog.dismiss() }
                .setPositiveButton(
                    R.string.sign_in_back_alert_dialog_apply_button_text
                ) { _, _ -> findNavController().popBackStack() }
                .show()
        }
    }

    private fun subscribeToFormFields() {
        viewBinding.apply {

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