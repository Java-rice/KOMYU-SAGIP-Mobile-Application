package com.mobile.komyusagip

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CreatePost : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createpost)

        val backToHome = findViewById<ImageButton>(R.id.backtohome)
        backToHome.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        val dropdownTypeOfCrime = findViewById<Spinner>(R.id.spinnerTypeOfCrime)
        val spinnerSelectedValueTextView = findViewById<TextView>(R.id.selectedSpinnerTypeOfCrime)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.typeOfCrime,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropdownTypeOfCrime.adapter = adapter
        dropdownTypeOfCrime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                spinnerSelectedValueTextView.text = selectedItem
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No implementation needed
            }
        }

        val submitButton = findViewById<Button>(R.id.SubmitButton)
        submitButton.setOnClickListener {
            validateAndSubmit()
        }
    }

    private fun validateAndSubmit() {
        val addDescription = findViewById<TextInputEditText>(R.id.addDesciption)
        val spinnerSelectedValueTextView = findViewById<TextView>(R.id.selectedSpinnerTypeOfCrime)

        val description = addDescription.text?.toString()?.trim()
        val typeOfCrime = spinnerSelectedValueTextView.text.toString()

        if (description.isNullOrEmpty()) {
            addDescription.error = "Add description"
            return
        }

        // Get current user ID
        val userId = auth.currentUser?.uid
        userId?.let { uid ->
            // Reference to the user's document
            val userRef = db.collection("user").document(uid)

            // Create a map for the post data
            val postData = hashMapOf(
                "description" to description,
                "typeOfCrime" to typeOfCrime
            )

            // Add the post to the "post" subcollection of the user's document
            userRef.collection("post")
                .add(postData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Post submitted successfully", Toast.LENGTH_SHORT).show()
                    // Redirect user to home screen or any other screen as desired
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to submit post: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } ?: run {
            // User not authenticated, handle accordingly
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }
}
