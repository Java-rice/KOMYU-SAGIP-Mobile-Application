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
            val sFirstName = editTextFirstName.text.toString()
            val sLastName = editTextLastName.text.toString()
            val sEmail = editTextEmail.text.toString()
            val sPhoneNumber = editTextPhoneNumber.text.toString()
            val sPassword = editTextPassword.text.toString()
            val userId = UUID.randomUUID().toString()

            userModel = UserModel(sFirstName, sLastName, sEmail, sPhoneNumber, sPassword, userId)
            db.collection("user").document(userId).set(userModel)
                .addOnSuccessListener {
                    startActivity(Intent(this, CreateProfile::class.java))
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error making the account", Toast.LENGTH_SHORT).show()
                }
        }
    }
}