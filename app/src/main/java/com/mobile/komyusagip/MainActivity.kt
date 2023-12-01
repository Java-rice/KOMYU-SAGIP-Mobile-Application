package com.mobile.komyusagip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonClick = findViewById<Button>(R.id.button_click)
        buttonClick.setOnClickListener {
            val intent = Intent(this, signup_page::class.java)
            startActivity(intent)
        }
    }
}