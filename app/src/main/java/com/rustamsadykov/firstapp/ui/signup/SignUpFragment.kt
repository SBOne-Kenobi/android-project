package com.rustamsadykov.firstapp.ui.signup

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.CheckBox
import androidx.activity.addCallback
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rustamsadykov.firstapp.R
import com.rustamsadykov.firstapp.databinding.FragmentSignUpBinding
import com.rustamsadykov.firstapp.ui.base.BaseFragment
import com.rustamsadykov.firstapp.utils.extentions.getSpannedString
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private val viewBinding by viewBinding(FragmentSignUpBinding::bind)

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBackButtonPressed()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            backButton.setOnClickListener {
                onBackButtonPressed()
            }
            signUpButton.setOnClickListener {
                viewModel.signUp(
                    firstname = viewBinding.firstnameEditText.text?.toString() ?: "",
                    lastname = viewBinding.lastnameEditText.text?.toString() ?: "",
                    nickname = viewBinding.nicknameEditText.text?.toString() ?: "",
                    email = viewBinding.emailEditText.text?.toString() ?: "",
                    password = viewBinding.passwordEditText.text?.toString() ?: ""
                )
            }
            termsAndConditionsCheckBox.setClubRulesText {
                startActivity(Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://policies.google.com/terms")
                ))
            }
        }

        subscribeToFormFields()
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventsFlow.collect { event ->
                    when (event) {
                        is SignUpViewModel.Event.SignUpEmailConfirmationRequired -> {
                            findNavController().navigate(R.id.action_signUpFragment_to_emailConfirmationFragment)
                        }
                        else -> {
                            // nothing
                        }
                    }
                }
            }
        }
    }

    private fun onBackButtonPressed() {
        viewBinding.apply {
            val firstname = firstnameEditText.text?.toString()
            val lastname = lastnameEditText.text?.toString()
            val nickname = nicknameEditText.text?.toString()
            val email = emailEditText.text?.toString()
            val password = passwordEditText.text?.toString()
            if (firstname.isNullOrBlank()
                && lastname.isNullOrBlank()
                && nickname.isNullOrBlank()
                && email.isNullOrBlank()
                && password.isNullOrBlank()
            ) {
                findNavController().popBackStack()
                return
            }
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.common_back_alert_dialog_text)
                .setNegativeButton(R.string.common_back_alert_dialog_cancel_button_text) { dialog, _ ->
                    dialog?.dismiss()
                }
                .setPositiveButton(R.string.common_back_alert_dialog_apply_button_text) { _, _ ->
                    findNavController().popBackStack()
                }
                .show()
        }
    }

    private fun subscribeToFormFields() {
        viewBinding.apply {

            decideSignUpButtonEnabledState()

            firstnameEditText.doAfterTextChanged { firstname ->
                decideSignUpButtonEnabledState(
                    firstname = firstname?.toString()
                )
            }

            lastnameEditText.doAfterTextChanged { lastname ->
                decideSignUpButtonEnabledState(
                    lastname = lastname?.toString()
                )
            }
            nicknameEditText.doAfterTextChanged { nickname ->
                decideSignUpButtonEnabledState(
                    nickname = nickname?.toString()
                )
            }
            emailEditText.doAfterTextChanged { email ->
                decideSignUpButtonEnabledState(
                    email = email?.toString()
                )
            }
            passwordEditText.doAfterTextChanged { password ->
                decideSignUpButtonEnabledState(
                    password = password?.toString()
                )
            }

            termsAndConditionsCheckBox.setOnCheckedChangeListener { _, isChecked ->
                decideSignUpButtonEnabledState(
                    termsIsChecked = isChecked
                )
            }
        }
    }

    private fun CheckBox.setClubRulesText(
        clubRulesClickListener: () -> Unit
    ) {
        movementMethod = LinkMovementMethod.getInstance()

        val clubRulesClickSpan = object : ClickableSpan() {
            override fun onClick(widget: View) = clubRulesClickListener()
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = resources.getColor(R.color.purple_200, null)
            }
        }

        text = resources.getSpannedString(
            R.string.sign_up_terms_and_conditions_template,
            buildSpannedString {
                inSpans(clubRulesClickSpan) {
                    append(resources.getString(R.string.sign_up_club_rules))
                }
            }
        )

    }

    private fun decideSignUpButtonEnabledState(
        firstname: String? = null,
        lastname: String? = null,
        nickname: String? = null,
        email: String? = null,
        password: String? = null,
        termsIsChecked: Boolean? = null
    ) {
        viewBinding.apply {

            val firstnameIn = firstname ?: firstnameEditText.text?.toString()
            val lastnameIn = lastname ?: lastnameEditText.text?.toString()
            val nicknameIn = nickname ?: nicknameEditText.text?.toString()
            val emailIn = email ?: emailEditText.text?.toString()
            val passwordIn = password ?: passwordEditText.text?.toString()
            val termsIsCheckedIn = termsIsChecked ?: termsAndConditionsCheckBox.isChecked

            signUpButton.isEnabled = !firstnameIn.isNullOrBlank()
                && !lastnameIn.isNullOrBlank()
                && !nicknameIn.isNullOrBlank()
                && !emailIn.isNullOrBlank()
                && !passwordIn.isNullOrBlank()
                && termsIsCheckedIn
        }
    }

}