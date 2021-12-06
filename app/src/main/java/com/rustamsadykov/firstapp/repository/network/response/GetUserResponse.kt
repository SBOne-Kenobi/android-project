package com.rustamsadykov.firstapp.repository.network.response

import com.rustamsadykov.firstapp.domain.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class GetUsersResponse(
    @Json(name = "data") val data: List<User>
)