package com.rustamsadykov.firstapp.ui.userlist

import androidx.lifecycle.viewModelScope
import com.rustamsadykov.firstapp.domain.User
import com.rustamsadykov.firstapp.repository.network.Api
import com.rustamsadykov.firstapp.ui.base.BaseViewModel
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class UserListViewModel : BaseViewModel() {

    sealed class ViewState {
        object Loading : ViewState()
        data class Data(val userList: List<User>) : ViewState()
    }

    private val _viewState: MutableStateFlow<ViewState> =
        MutableStateFlow(ViewState.Loading)

    val viewState: Flow<ViewState>
        get() = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            _viewState.emit(ViewState.Loading)
            val users = loadUsers()
            _viewState.emit(ViewState.Data(users))
        }
    }

    private suspend fun loadUsers(): List<User> {
        return provideApi().getUsers().data
    }

    private fun provideApi(): Api {
        return Retrofit.Builder()
            .client(provideOkHttpClient())
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
            .build()
            .create(Api::class.java)
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    private fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }
}