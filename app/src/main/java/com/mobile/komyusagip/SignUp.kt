package com.mobile.komyusagip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.mobile.komyusagip.model.UserModel
import java.util.UUID
import android.text.TextUtils

class SignUp : AppCompatActivity() {
    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var userModel: UserModel

    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val backToMainClick = findViewById<ImageButton>(R.id.backtoMain)
        backToMainClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        editTextFirstName = findViewById(R.id.first_name)
        editTextLastName = findViewById(R.id.editText2)
        editTextEmail = findViewById(R.id.editText)
        editTextPhoneNumber = findViewById(R.id.editText3)
        editTextPassword = findViewById(R.id.editTextTextPassword)

        val signupClick = findViewById<Button>(R.id.signupNext)
        signupClick.setOnClickListener {
            val sFirstName = editTextFirstName.text.toString().trim()
            val sLastName = editTextLastName.text.toString().trim()
            val sEmail = editTextEmail.text.toString().trim()
            val sPhoneNumber = editTextPhoneNumber.text.toString().trim()
            val sPassword = editTextPassword.text.toString().trim()

            fun isValidEmail(email: String): Boolean {
                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                return email.matches(emailPattern.toRegex())
            }

            fun validateFields(){
                editTextFirstName.error = null
                editTextLastName.error = null
                editTextEmail.error = null
                editTextPhoneNumber.error = null
                editTextPassword.error = null

                var hasError = false
                if (TextUtils.isEmpty(sFirstName)) {
                    editTextFirstName.error = "First name is required"
                    hasError = true
                }

                if (TextUtils.isEmpty(sLastName)) {
                    editTextLastName.error = "Last name is required"
                    hasError = true
                }

                if (TextUtils.isEmpty(sEmail)) {
                    editTextEmail.error = "Email is required"
                    hasError = true
                } else if (!isValidEmail(sEmail)) {
                    editTextEmail.error = "Invalid email address"
                    hasError = true
                }

                if (TextUtils.isEmpty(sPhoneNumber)) {
                    editTextPhoneNumber.error = "Phone number is required"
                    hasError = true
                } else if (sPhoneNumber.length < 11 || sPhoneNumber.length > 11) {
                    editTextPhoneNumber.error = "Please enter a 11-digit phone number"
                    hasError = true
                }

                if (TextUtils.isEmpty(sPassword)) {
                    editTextPassword.error = "Password is required"
                    hasError = true
                } else if (sPassword.length < 6) {
                    editTextPassword.error = "Password must be at least 6 characters"
                    hasError = true
                }

                if (!hasError) {
                    val userId = sEmail
                    userModel = UserModel(sFirstName, sLastName, sEmail, sPhoneNumber, sPassword, userId)
                    db.collection("user").document(userId).set(userModel)
                        .addOnSuccessListener {
                            val intent = Intent(this, CreateProfile::class.java)
                            intent.putExtra("userId", userId)  // Pass userId to CreateProfile activity
                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error making the account", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            validateFields()
        }
    }
}