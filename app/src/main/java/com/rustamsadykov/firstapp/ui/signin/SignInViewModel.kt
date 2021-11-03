package com.rustamsadykov.firstapp.ui.signin

import androidx.lifecycle.viewModelScope
import com.rustamsadykov.firstapp.repository.AuthRepository
import com.rustamsadykov.firstapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SignInViewModel : BaseViewModel() {

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            AuthRepository.signIn(email, password)
        }
    }

}