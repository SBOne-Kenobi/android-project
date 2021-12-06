package com.rustamsadykov.firstapp.ui.emailconfirmation

import androidx.lifecycle.viewModelScope
import com.rustamsadykov.firstapp.repository.OldAuthRepository
import com.rustamsadykov.firstapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class EmailConfirmationViewModel : BaseViewModel() {

    fun signIn() {
        viewModelScope.launch {
            OldAuthRepository.signIn("", "")
        }
    }

}