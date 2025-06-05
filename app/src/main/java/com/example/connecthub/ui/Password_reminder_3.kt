package com.example.connecthub.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.connecthub.R
import com.example.connecthub.model.ErrorResponse
import com.example.connecthub.model.ForgottenPasswordRequest
import com.example.connecthub.model.ForgottenPasswordResponse
import com.example.connecthub.model.LoginRequest
import com.example.connecthub.model.LoginResponse
import com.example.connecthub.network.RetrofitClient
import com.example.connecthub.ui.Login
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Password_reminder_3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_password_reminder_3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val passwordInput = findViewById<EditText>(R.id.password_input)
        val passwordConfirm = findViewById<EditText>(R.id.password_confirm)


        val change_password = findViewById<Button>(R.id.change_password_button)
        val id = intent.getStringExtra("id")?.toIntOrNull() ?:-1
        change_password.setOnClickListener {
            val request = ForgottenPasswordRequest(
                password = passwordInput.text.toString(),
                password_confirmation = passwordConfirm.text.toString()
            )

            RetrofitClient.authService.forgotPassword(id, request).enqueue(object :
                Callback<ForgottenPasswordResponse> {
                override fun onResponse(call: Call<ForgottenPasswordResponse>, response: Response<ForgottenPasswordResponse>) {
                    if (response.isSuccessful) {
                        val message = response.body()?.message
                        Toast.makeText(
                            this@Password_reminder_3,
                            "${message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@Password_reminder_3, Login::class.java)
                        startActivity(intent)
                    } else {
                        val errorBody = response.errorBody()?.string()
                        if (!errorBody.isNullOrEmpty()) {
                            try {
                                val gson = Gson()
                                val errorResponse =
                                    gson.fromJson(errorBody, ErrorResponse::class.java)
                                val messages = errorResponse.detail.joinToString("\n") { it.msg }
                                Toast.makeText(this@Password_reminder_3, messages, Toast.LENGTH_LONG).show()
                            } catch (e: Exception) {
                                Toast.makeText(
                                    this@Password_reminder_3,
                                    "Error: $errorBody",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            Toast.makeText(this@Password_reminder_3, "Unknown error", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                override fun onFailure(call: Call<ForgottenPasswordResponse>, t: Throwable) {
                    Toast.makeText(this@Password_reminder_3, "Network error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}