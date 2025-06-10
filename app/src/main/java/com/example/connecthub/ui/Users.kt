package com.example.connecthub.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.connecthub.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class Users : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_users)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val bottomNav = findViewById<BottomNavigationView>(R.id.menu)
        bottomNav.selectedItemId = R.id.nav_Users


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
        if (this::class.java != Users::class.java) {
            startActivity(Intent(this, Users::class.java))
        }
        overridePendingTransition(0, 0)
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