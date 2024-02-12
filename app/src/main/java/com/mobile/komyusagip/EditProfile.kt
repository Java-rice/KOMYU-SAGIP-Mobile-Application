package com.mobile.komyusagip

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


class EditProfile : AppCompatActivity() {
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

        val inputs = listOf(firstName, lastName, username, location)

        val isValidInput = inputs.any { it.isNotEmpty() }

        if (isValidInput) {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please fill in at least one field", Toast.LENGTH_SHORT).show()
        }
    }
}
