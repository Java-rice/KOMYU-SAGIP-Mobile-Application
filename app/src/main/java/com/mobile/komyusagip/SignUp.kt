package com.mobile.komyusagip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.text.TextUtils
import android.widget.EditText

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val backToMainClick = findViewById<ImageButton>(R.id.backtoMain)
        backToMainClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //Signup button click action
        val signupClick = findViewById<Button>(R.id.signupNext)
        signupClick.setOnClickListener {
            validateFields()
        }
    }
    // Switch activity to create profile
    private fun proceedToCreateProfile() {
        val intent = Intent(this, CreateProfile::class.java)
        startActivity(intent)
    }

    // Validate input fields
    private fun validateFields() {
        val firstname = findViewById<EditText>(R.id.first_name)
        val lastname = findViewById<EditText>(R.id.last_name)
        val email = findViewById<EditText>(R.id.email)
        val phonenumber = findViewById<EditText>(R.id.phone_number)
        val password = findViewById<EditText>(R.id.password)

        val firstName = firstname.text.toString().trim()
        val lastName = lastname.text.toString().trim()
        val eMail = email.text.toString().trim()
        val phoneNum = phonenumber.text.toString().trim()
        val pass = password.text.toString().trim()

        // Reset errors
        firstname.error = null
        lastname.error = null
        email.error = null
        phonenumber.error = null
        password.error = null

        var hasError = false

        if (TextUtils.isEmpty(firstName)) {
            firstname.error = "First name is required"
            hasError = true
        }

        if (TextUtils.isEmpty(lastName)) {
            lastname.error = "Last name is required"
            hasError = true
        }

        if (TextUtils.isEmpty(eMail)) {
            email.error = "Email is required"
            hasError = true
        } else if (!isValidEmail(eMail)) {
            email.error = "Invalid email address"
            hasError = true
        }

        if (TextUtils.isEmpty(phoneNum)) {
            phonenumber.error = "Phone number is required"
            hasError = true
        } else if (phoneNum.length < 11 || phoneNum.length > 11) {
            phonenumber.error = "Please enter a 11-digit phone number"
            hasError = true
        }

        if (TextUtils.isEmpty(pass)) {
            password.error = "Password is required"
            hasError = true
        } else if (pass.length < 6) {
            password.error = "Password must be at least 6 characters"
            hasError = true
        }

        if (!hasError) {
            proceedToCreateProfile()
        }
    }

    // Checks email format
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }
}