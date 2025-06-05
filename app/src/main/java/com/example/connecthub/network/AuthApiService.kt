package com.example.connecthub.network

import com.example.connecthub.model.CheckEmailResponse
import com.example.connecthub.model.ForgottenPasswordRequest
import com.example.connecthub.model.ForgottenPasswordResponse
import com.example.connecthub.model.LoginRequest
import com.example.connecthub.model.LoginResponse
import com.example.connecthub.model.RegisterRequest
import com.example.connecthub.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AuthApiService {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("auth/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @PUT("auth/change-password")
    fun changePassword(
        @Header("access-token") accessToken: String,
        @Header("token-type") tokenType: String,
        @Header("refresh-token") refreshToken: String?,
        @Body request: ChangePasswordRequest
    ): Call<Any>

    @PUT("auth/forgot-password")
    fun forgotPassword(
        @Query("user_id") user_id: Int,
        @Body request: ForgottenPasswordRequest
    ): Call<ForgottenPasswordResponse>

    @GET("auth/check-email")
    fun getEmail(@Query("email") email: String): Call<okhttp3.ResponseBody>
}