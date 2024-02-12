package com.mobile.komyusagip

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val backToMain = findViewById<ImageButton>(R.id.back)
        backToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.enterPassword)

        val loginClick = findViewById<Button>(R.id.loginAuth)
        loginClick.setOnClickListener {
            val sEmail = editTextEmail.text.toString().trim()
            val sPassword = editTextPassword.text.toString().trim()

            auth.signInWithEmailAndPassword(sEmail, sPassword).addOnCompleteListener {
                if(it.isSuccessful){
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "There is an error logging you in.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}