package com.mobile.komyusagip

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView

class EditProfile : AppCompatActivity() {
    private lateinit var profilePicture: CircleImageView
    private lateinit var profileName: TextView
    private lateinit var profileUsername: TextView
    private lateinit var profileLocation: TextView
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val backToProfileClick = findViewById<ImageButton>(R.id.backToProfile)
        backToProfileClick.setOnClickListener {
            finish()
        }

        val saveProfileClick = findViewById<Button>(R.id.profileSave)
        saveProfileClick.setOnClickListener {
            validateAndSubmit()
        }

    }


    private fun validateAndSubmit() {
        val entryFirstName = findViewById<TextInputEditText>(R.id.entry_first_name)
        val entryLastName = findViewById<TextInputEditText>(R.id.entry_last_name)
        val entryUsername = findViewById<TextInputEditText>(R.id.entry_username)
        val entryLocation = findViewById<TextInputEditText>(R.id.entry_location)

        val firstName = entryFirstName.text.toString().trim()
        val lastName = entryLastName.text.toString().trim()
        val username = entryUsername.text.toString().trim()
        val location = entryLocation.text.toString().trim()

        // Get current user ID
        val userId = auth.currentUser?.uid

        userId?.let {
            // Update the firstName and lastName in "user" collection
            val userRef = db.collection("user").document(userId)
            userRef.update(mapOf(
                "firstName" to firstName,
                "lastName" to lastName
            )).addOnSuccessListener {
                val profileRef = userRef.collection("profiles")
                profileRef.get().addOnSuccessListener { profileQuerySnapshot ->
                    if (!profileQuerySnapshot.isEmpty) {
                        val profileDocument = profileQuerySnapshot.documents[0]

                        // Update the fields in the profile document
                        profileDocument.reference.update(mapOf(
                            "username" to username,
                            "location" to location,
                            //"imageUrl" to imageUrl
                        )).addOnSuccessListener {
                            // Navigate back to the profile screen after updating
                            finish()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, "Failed to update profile: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to fetch profile: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(this, "Failed to update profile: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            // User not authenticated, handle accordingly
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }
}

