package com.example.connecthub.model

data class ForgottenPasswordRequest(
    val password: String,
    val password_confirmation: String
)
