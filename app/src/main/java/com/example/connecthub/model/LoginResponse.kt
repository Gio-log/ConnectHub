package com.example.connecthub.model

data class LoginResponse(
    val access_token: String,
    val token_type: String,
    val refresh_token: String?
)
