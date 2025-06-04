package com.example.connecthub

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        val register_complete = findViewById<Button>(R.id.register_complete_button)
        register_complete.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

    }
}