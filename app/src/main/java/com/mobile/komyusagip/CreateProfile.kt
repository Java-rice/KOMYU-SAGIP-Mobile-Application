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
class CreateProfile : AppCompatActivity() {
    private lateinit var editUsername: EditText
    private lateinit var editLocation: EditText
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createprofile)

        val backToSignUpClick = findViewById<ImageButton>(R.id.backtosignup)
        backToSignUpClick.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        editUsername = findViewById(R.id.editText5)
        editLocation = findViewById(R.id.editText6)

        val finishSetUp = findViewById<Button>(R.id.createProfileNext)
        finishSetUp.setOnClickListener {
            val sUsername = editUsername.text.toString().trim()
            val sLocation = editLocation.text.toString().trim()
            val userId = intent.getStringExtra("userId")

            if (!userId.isNullOrBlank()) {
                val profileData = hashMapOf(
                    "username" to sUsername,
                    "location" to sLocation
                )

                db.collection("user").document(userId).collection("profiles").add(profileData)
                    .addOnSuccessListener {
                        // Success
                        Toast.makeText(this, "Profile created successfully", Toast.LENGTH_SHORT).show()
                        // Navigate to the next activity or perform other actions if needed
                        val intent = Intent(this, Home::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        // Handle failure
                        Toast.makeText(this, "Error creating profile: $it", Toast.LENGTH_SHORT).show()
                    }
            } else {
                // Handle the case where userId is null or empty
                Toast.makeText(this, "Error: UserId not found", Toast.LENGTH_SHORT).show()
            }

        }
    }
}