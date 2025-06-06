package com.example.connecthub.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.connecthub.R

class Main_menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val access_token = intent.getStringExtra("access_token")
        val token_type = intent.getStringExtra("token_type")
        val login = intent.getStringExtra("login")

        val NoTalks = findViewById<TextView>(R.id.NoTalks)
        val NoInterests = findViewById<TextView>(R.id.NoInterests)
        val NoTalksFullText = "Nie masz przeprowadzonej jeszcze z nikim rozmowy. Kliknij tutaj, aby przejść do miejsca gdzie możesz zacząć rozmawiać ze znajomymi."
        val NoInterestsFullText = "Nie wybrałęś jeszcze żadnych zainteresowań. Kliknij tutaj aby wybrać swoje zainteresowania!"
        val clickabletext = "Kliknij tutaj"
        NoTalks.text = NoInterestsFullText
        NoInterests.text = NoInterestsFullText

        val spannableStringNoTalks = SpannableString(NoTalksFullText)
        val startNoTalks = NoTalksFullText.indexOf(clickabletext)
        val endNoTalks = startNoTalks + clickabletext.length

        val spannableStringNoInterests = SpannableString(NoInterestsFullText)
        val startNoInterests = NoInterestsFullText.indexOf(clickabletext)
        val endNoInterests = startNoInterests + clickabletext.length

        val clickableSpanNoTalks = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(widget.context, "Kliknąłeś bez rozmów", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@Main_menu, Chat::class.java)
                intent.putExtra("access_token", access_token)
                intent.putExtra("token_type", token_type)
                intent.putExtra("login", login)
                startActivity(intent)
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.BLUE  // Set your clickable text color
                ds.isUnderlineText = false // Remove underline if you don't want it
            }
        }

        val clickableSpanNoInterests = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(widget.context, "Kliknąłeś bez zainteresowań", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@Main_menu, User_edit::class.java)
                intent.putExtra("access_token", access_token)
                intent.putExtra("token_type", token_type)
                intent.putExtra("login", login)
                startActivity(intent)
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.BLUE  // Set your clickable text color
                ds.isUnderlineText = false // Remove underline if you don't want it
            }
        }

        spannableStringNoTalks.setSpan(
            clickableSpanNoTalks,
            startNoTalks,
            endNoTalks,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableStringNoInterests.setSpan(
            clickableSpanNoInterests,
            startNoInterests,
            endNoInterests,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        NoTalks.text = spannableStringNoTalks
        NoTalks.movementMethod = android.text.method.LinkMovementMethod.getInstance()

        NoInterests.text = spannableStringNoInterests
        NoInterests.movementMethod = android.text.method.LinkMovementMethod.getInstance()
    }
}