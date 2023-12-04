package com.mobile.komyusagip

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val backToMain = findViewById<ImageButton>(R.id.back)
        backToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

//        val loginClick = findViewById<Button>(R.id.loginauth)
//        loginClick.setOnClickListener {
//            val intent = Intent(this, Home::class.java)
//            startActivity(intent)
//        }
    }
}