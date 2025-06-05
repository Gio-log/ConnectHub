package com.example.connecthub.network

data class ChangePasswordRequest(
    val password: String,
    val password_confirmation: String,
    val current_password: String
)
