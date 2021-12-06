package com.rustamsadykov.firstapp.ui.signup

import androidx.lifecycle.viewModelScope
import com.rustamsadykov.firstapp.repository.OldAuthRepository
import com.rustamsadykov.firstapp.ui.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignUpViewModel : BaseViewModel() {

    private val _eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow: Flow<Event>
        get() = _eventChannel.receiveAsFlow()

    fun signUp(
        firstname: String,
        lastname: String,
        nickname: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                OldAuthRepository.signUp(
                    firstname,
                    lastname,
                    nickname,
                    email,
                    password
                )
                _eventChannel.send(Event.SignUpSuccess)
            } catch (error: Exception) {
                _eventChannel.send(Event.SignUpEmailConfirmationRequired)
            }
        }
    }

    sealed class Event {
        object SignUpSuccess : Event()
        object SignUpEmailConfirmationRequired: Event()
    }

}