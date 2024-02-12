package com.mobile.komyusagip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { false }
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.login_click)
        loginButton.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }

        val buttonClick = findViewById<Button>(R.id.button_click)
        buttonClick.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }

        if (auth.currentUser != null){
                startActivity(Intent(this, Home::class.java))
                finish()
        }
    }
}


