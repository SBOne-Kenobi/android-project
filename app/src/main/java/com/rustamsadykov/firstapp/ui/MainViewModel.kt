package com.rustamsadykov.firstapp.ui

import com.rustamsadykov.firstapp.repository.AuthRepository
import com.rustamsadykov.firstapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
): BaseViewModel() {

    suspend fun isAuthorizedFlow(): Flow<Boolean> =
        authRepository.isAuthorizedFlow()
}