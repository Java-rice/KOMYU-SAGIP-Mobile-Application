package com.mobile.komyusagip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.mobile.komyusagip.model.UserModel

class CreateProfile : AppCompatActivity() {
    private lateinit var editProfileImage: EditText
    private lateinit var editUsername: EditText
    private lateinit var editLocation: EditText
    private lateinit var userModel: UserModel
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createprofile)


        val backToSignUpClick = findViewById<ImageButton>(R.id.backtosignup)
        backToSignUpClick.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        val finishSetUp = findViewById<Button>(R.id.createProfileNext)
        finishSetUp.setOnClickListener {
            startActivity(Intent(this, Home::class.java))
            finish()
        }
    }
}