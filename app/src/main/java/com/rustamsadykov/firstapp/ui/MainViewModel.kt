package com.rustamsadykov.firstapp.ui

import com.rustamsadykov.firstapp.repository.OldAuthRepository
import com.rustamsadykov.firstapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class MainViewModel : BaseViewModel() {

    val isAuthorizedFlow: Flow<Boolean> = OldAuthRepository.isAuthorizedFlow

}