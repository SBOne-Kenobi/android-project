package com.rustamsadykov.firstapp.ui.emailconfirmation

import androidx.lifecycle.viewModelScope
import com.rustamsadykov.firstapp.interactor.AuthInteractor
import com.rustamsadykov.firstapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailConfirmationViewModel @Inject constructor(
    private val authInteractor: AuthInteractor
): BaseViewModel() {

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authInteractor.signInWithEmail(email, password)
        }
    }

}