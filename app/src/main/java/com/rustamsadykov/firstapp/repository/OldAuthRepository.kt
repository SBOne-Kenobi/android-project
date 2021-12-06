package com.rustamsadykov.firstapp.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.lang.Exception

object OldAuthRepository {

    private val _isAuthorizedFlow = MutableStateFlow(false)
    val isAuthorizedFlow: Flow<Boolean>
        get() = _isAuthorizedFlow.asStateFlow()

    suspend fun signIn(email: String, password: String) {
        _isAuthorizedFlow.emit(true)
    }

    suspend fun logout() {
        _isAuthorizedFlow.emit(false)
    }

    suspend fun signUp(
        firstname: String,
        lastname: String,
        nickname: String,
        email: String,
        password: String
    ) {
        throw Exception("not impl")
    }

}