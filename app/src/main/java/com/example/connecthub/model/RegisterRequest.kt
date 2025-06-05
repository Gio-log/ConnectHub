package com.example.connecthub.model

data class RegisterRequest(
    val password: String,
    val password_confirmation: String,
    val login: String,
    val email: String,
    val phone_number: String,
    val first_name: String,
    val last_name: String
)