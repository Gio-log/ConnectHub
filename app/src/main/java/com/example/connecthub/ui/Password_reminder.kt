package com.example.connecthub.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.connecthub.R
import com.example.connecthub.model.CheckEmailResponse
import com.example.connecthub.model.ErrorResponse
import com.example.connecthub.network.RetrofitClient
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Password_reminder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_password_reminder)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sendEmailButton = findViewById<Button>(R.id.send_email_button)
        val emailInput = findViewById<TextInputEditText>(R.id.textInputEditText)


        sendEmailButton.setOnClickListener {
            val email = emailInput.text.toString()

            RetrofitClient.authService.getEmail(email).enqueue(object :
                Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        val id = response.body()?.string()
                        Toast.makeText(
                            this@Password_reminder,
                            "Email to reset password sent",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@Password_reminder, Password_reminder_2::class.java)
                        intent.putExtra("id", id)
                        startActivity(intent)
                    } else {
                        val errorBody = response.errorBody()?.string()
                        if (!errorBody.isNullOrEmpty()) {
                            try {
                                val gson = Gson()
                                val errorResponse =
                                    gson.fromJson(errorBody, ErrorResponse::class.java)
                                val messages = errorResponse.detail.joinToString("\n") { it.msg }
                                Toast.makeText(this@Password_reminder, messages, Toast.LENGTH_LONG).show()
                            } catch (e: Exception) {
                                Toast.makeText(
                                    this@Password_reminder,
                                    "Error: $errorBody",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            Toast.makeText(this@Password_reminder, "Unknown error", Toast.LENGTH_LONG).show()
                        }
                    }

                }override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@Password_reminder, "Network error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}