package com.example.connecthub.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.connecthub.R
import com.example.connecthub.model.ErrorResponse
import com.example.connecthub.model.LoginRequest
import com.example.connecthub.model.LoginResponse
import com.example.connecthub.network.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val login = findViewById<EditText>(R.id.login_field)
        val password = findViewById<EditText>(R.id.password_field)

        val register=findViewById<TextView>(R.id.register)
        register.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        val password_reset=findViewById<TextView>(R.id.password_reset)
        password_reset.setOnClickListener {
            val intent = Intent(this, Password_reminder::class.java)
            startActivity(intent)
        }

        val login_button = findViewById<Button>(R.id.login_button)
        login_button.setOnClickListener {
            val request = LoginRequest(
                login = login.text.toString(),
                password = password.text.toString()
            )

            RetrofitClient.authService.login(request).enqueue(object :
                Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val access_token = response.body()?.access_token
                        val token_type = response.body()?.token_type
                        Toast.makeText(
                            this@Login,
                            "Logged in: ${login.text.toString()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@Login, Main_menu::class.java)
                        intent.putExtra("access_token", access_token)
                        intent.putExtra("token_type", token_type)
                        startActivity(intent)
                    } else {
                        val errorBody = response.errorBody()?.string()
                        if (!errorBody.isNullOrEmpty()) {
                            try {
                                val gson = Gson()
                                val errorResponse =
                                    gson.fromJson(errorBody, ErrorResponse::class.java)
                                val messages = errorResponse.detail.joinToString("\n") { it.msg }
                                Toast.makeText(this@Login, messages, Toast.LENGTH_LONG).show()
                            } catch (e: Exception) {
                                Toast.makeText(
                                    this@Login,
                                    "Error: $errorBody",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            Toast.makeText(this@Login, "Unknown error", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@Login, "Network error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

    }

}