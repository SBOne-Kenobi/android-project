package com.rustamsadykov.firstapp.ui

import androidx.lifecycle.viewModelScope
import com.rustamsadykov.firstapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

    private val _isAuthorizedFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isAuthorizedFlow: Flow<Boolean>
        get() = _isAuthorizedFlow.asStateFlow()

}