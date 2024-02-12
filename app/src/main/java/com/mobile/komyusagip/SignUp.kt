package com.mobile.komyusagip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.mobile.komyusagip.model.UserModel
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.mobile.komyusagip.databinding.ActivitySignupBinding

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var userModel: UserModel

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backtoMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.signupNext.setOnClickListener {
            val sFirstName = binding.firstName.text.toString().trim()
            val sLastName = binding.editText2.text.toString().trim()
            val sEmail = binding.editText.text.toString().trim()
            val sPhoneNumber = binding.editText3.text.toString().trim()
            val sPassword = binding.editTextTextPassword.text.toString().trim()

            fun isValidEmail(email: String): Boolean {
                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                return email.matches(emailPattern.toRegex())
            }

            fun validateFields(){
                binding.firstName.error = null
                binding.editText2.error = null
                binding.editText.error = null
                binding.editText3.error = null
                binding.editTextTextPassword.error = null

                var hasError = false
                if (TextUtils.isEmpty(sFirstName)) {
                    binding.firstName.error = "First name is required"
                    hasError = true
                }

                if (TextUtils.isEmpty(sLastName)) {
                    binding.editText2.error = "Last name is required"
                    hasError = true
                }

                if (TextUtils.isEmpty(sEmail)) {
                    binding.editText.error = "Email is required"
                    hasError = true
                } else if (!isValidEmail(sEmail)) {
                    binding.editText.error = "Invalid email address"
                    hasError = true
                }

                if (TextUtils.isEmpty(sPhoneNumber)) {
                    binding.editText3.error = "Phone number is required"
                    hasError = true
                } else if (sPhoneNumber.length < 11 || sPhoneNumber.length > 11) {
                    binding.editText3.error = "Please enter a 11-digit phone number"
                    hasError = true
                }

                if (TextUtils.isEmpty(sPassword)) {
                    binding.editTextTextPassword.error = "Password is required"
                    hasError = true
                } else if (sPassword.length < 6) {
                    binding.editTextTextPassword.error = "Password must be at least 6 characters"
                    hasError = true
                }

                if (!hasError) {
                    auth.createUserWithEmailAndPassword(sEmail, sPassword).addOnCompleteListener {
                        if(it.isSuccessful){
                            val userId = auth.currentUser?.uid
                            userModel = UserModel(sFirstName, sLastName, sEmail, sPhoneNumber, sPassword, userId)
                            db.collection("user").document(userId!!).set(userModel)
                                .addOnSuccessListener {
                                    val intent = Intent(this, CreateProfile::class.java)
                                    intent.putExtra("userId", userId)  // Pass userId to CreateProfile activity
                                    startActivity(intent)
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Error in saving your information.", Toast.LENGTH_SHORT).show()
                                }
                        } else{
                            Toast.makeText(this, "Error making the account, ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            validateFields()
        }
    }
}
