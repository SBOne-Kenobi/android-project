package com.rustamsadykov.firstapp.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "user_name") val userName: String,
    @Json(name = "email") val groupName: String,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "second_name") val secondName: String,
    @Json(name = "picture") val avatarUrl: String? = null,
)
