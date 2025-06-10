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
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        val bottomNav = findViewById<BottomNavigationView>(R.id.menu)
        bottomNav.selectedItemId = R.id.nav_Main_menu


        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_Main_menu -> {
                    if (this !is Main_menu) {
                        val intent = Intent(this, Main_menu::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        startActivity(intent)
                        finish()
                    }
                    true
                }
                R.id.nav_Users -> {
                    if (this !is Users) {
                        val intent = Intent(this, Users::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        startActivity(intent)
                        finish()
                    }
                    true
                }
                R.id.nav_Chat -> {
                    if (this !is Chat) {
                        val intent = Intent(this, Chat::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        startActivity(intent)
                        finish()
                    }
                    true
                }
                R.id.nav_Settings -> {
                    if (this !is User_edit) {
                        val intent = Intent(this, User_edit::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        startActivity(intent)
                        finish()
                    }
                    true
                }
                else -> false
            }

        }
        if (this::class.java != Main_menu::class.java) {
            startActivity(Intent(this, Main_menu::class.java))
        }
        overridePendingTransition(0, 0)

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