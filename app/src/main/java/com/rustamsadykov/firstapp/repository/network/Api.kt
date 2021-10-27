package com.rustamsadykov.firstapp.repository.network

import com.rustamsadykov.firstapp.repository.network.response.GetUsersResponse
import retrofit2.http.GET

interface Api {

    @GET("users?per_page=10")
    suspend fun getUsers(): GetUsersResponse
}
