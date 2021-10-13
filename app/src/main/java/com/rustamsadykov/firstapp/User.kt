package com.rustamsadykov.firstapp

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "first_name") val userName: String,
    @Json(name = "email") val groupName: String,
    @Json(name = "avatar") val avatarUrl: String
)
