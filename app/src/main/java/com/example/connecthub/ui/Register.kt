package com.example.connecthub.ui

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.connecthub.R
import com.example.connecthub.model.ErrorResponse
import com.example.connecthub.model.RegisterRequest
import com.example.connecthub.model.RegisterResponse
import com.example.connecthub.network.RetrofitClient
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val login = findViewById<EditText>(R.id.textInputEditText)
        val firstName = findViewById<EditText>(R.id.textInputEditText2)
        val lastName = findViewById<EditText>(R.id.textInputEditText3)
        val phone = findViewById<EditText>(R.id.textInputEditText4)
        val email = findViewById<EditText>(R.id.textInputEditText5)
        val password = findViewById<EditText>(R.id.textInputEditText6)
        val registerButton = findViewById<Button>(R.id.register_complete_button)
        val genderGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)


        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val rb = findViewById<RadioButton>(checkedId)
        }
        val radioButton1 = findViewById<RadioButton>(R.id.radioButton1)
        val radioButton2 = findViewById<RadioButton>(R.id.radioButton2)

        radioButton1.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (radioButton1.isChecked) {
                    radioButton1.isChecked = false
                } else {
                    radioButton1.isChecked = true
                    radioButton2.isChecked = false
                }
            }
            true // Consume the touch event
        }

        radioButton2.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (radioButton2.isChecked) {
                    radioButton2.isChecked = false
                } else {
                    radioButton2.isChecked = true
                    radioButton1.isChecked = false
                }
            }
            true
        }

        registerButton.setOnClickListener {
            val request = RegisterRequest(
                email = email.text.toString(),
                password = password.text.toString(),
                password_confirmation = password.text.toString(),
                login = login.text.toString(),
                phone_number = phone.text.toString(),
                first_name = firstName.text.toString(),
                last_name = lastName.text.toString()
            )

            RetrofitClient.authService.register(request).enqueue(object :
                Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        Toast.makeText(
                            this@Register,
                            "Registered: ${user?.email}",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(Intent(this@Register, Register_2::class.java))
                    } else {
                        val errorBody = response.errorBody()?.string()
                        if (!errorBody.isNullOrEmpty()) {
                            try {
                                val gson = Gson()
                                val errorResponse =
                                    gson.fromJson(errorBody, ErrorResponse::class.java)
                                val messages = errorResponse.detail.joinToString("\n") { it.msg }
                                Toast.makeText(this@Register, messages, Toast.LENGTH_LONG).show()
                            } catch (e: Exception) {
                                Toast.makeText(
                                    this@Register,
                                    "Error: $errorBody",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            Toast.makeText(this@Register, "Unknown error", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(this@Register, "Network error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }
    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )
    }
}