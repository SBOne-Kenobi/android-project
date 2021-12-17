package com.rustamsadykov.firstapp.data.network

import com.haroldadmin.cnradapter.NetworkResponse
import com.rustamsadykov.firstapp.data.network.request.CreateProfileRequest
import com.rustamsadykov.firstapp.data.network.request.RefreshAuthTokensRequest
import com.rustamsadykov.firstapp.data.network.request.SignInWithEmailRequest
import com.rustamsadykov.firstapp.data.network.response.GetUsersResponse
import com.rustamsadykov.firstapp.data.network.response.VerificationTokenResponse
import com.rustamsadykov.firstapp.data.network.response.error.*
import com.rustamsadykov.firstapp.domain.AuthTokens
import com.rustamsadykov.firstapp.domain.User
import retrofit2.http.*

interface Api {

    @GET("users?per_page=10")
    suspend fun getUsers(): GetUsersResponse

    @POST("auth/sign-in-email")
    suspend fun signInWithEmail(
        @Body request: SignInWithEmailRequest
    ): NetworkResponse<AuthTokens, SignInWithEmailErrorResponse>

    @POST("auth/refresh-tokens")
    suspend fun refreshAuthTokens(
        @Body request: RefreshAuthTokensRequest
    ): NetworkResponse<AuthTokens, RefreshAuthTokensErrorResponse>

    @POST("registration/verification-code/send")
    suspend fun sendRegistrationVerificationCode(
        @Query("email") email: String
    ): NetworkResponse<Unit, SendRegistrationVerificationCodeErrorResponse>

    @POST("registration/verification-code/verify")
    suspend fun verifyRegistrationCode(
        @Query("code") code: String,
        @Query("email") email: String?
    ): NetworkResponse<VerificationTokenResponse, VerifyRegistrationCodeErrorResponse>

    @PUT("registration/create-profile")
    suspend fun createProfile(
        @Body request: CreateProfileRequest
    ): NetworkResponse<AuthTokens, CreateProfileErrorResponse>

    @GET("users/get-profile")
    suspend fun getProfile(): User
}
