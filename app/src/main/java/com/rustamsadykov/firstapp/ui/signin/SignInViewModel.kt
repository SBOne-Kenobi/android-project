package com.rustamsadykov.firstapp.ui.signin

import androidx.lifecycle.viewModelScope
import com.rustamsadykov.firstapp.repository.AuthRepository
import com.rustamsadykov.firstapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SignInViewModel : BaseViewModel() {

    fun signIn() {
        viewModelScope.launch {
            AuthRepository.signIn()
        }
    }

}