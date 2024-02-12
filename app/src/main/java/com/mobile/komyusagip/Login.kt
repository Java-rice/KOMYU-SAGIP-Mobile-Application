package com.mobile.komyusagip

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.mobile.komyusagip.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val loginClick = findViewById<Button>(R.id.loginAuth)
        loginClick.setOnClickListener {
            val sEmail = binding.email.text.toString().trim()
            val sPassword = binding.enterPassword.text.toString().trim()

            auth.signInWithEmailAndPassword(sEmail, sPassword).addOnCompleteListener {
                if(it.isSuccessful){
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Account doesn't exists.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}