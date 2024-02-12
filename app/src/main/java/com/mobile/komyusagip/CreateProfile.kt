package com.mobile.komyusagip

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mobile.komyusagip.databinding.ActivityCreateprofileBinding

class CreateProfile : AppCompatActivity() {
    private lateinit var binding: ActivityCreateprofileBinding
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private var imageUri: Uri? = null
    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            imageUri = it
            binding.imageButton.setImageURI(imageUri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backtosignup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        binding.imageButton.setOnClickListener {
            selectImage.launch("image/*")
        }


        binding.createProfileNext.setOnClickListener {
            val sUsername = binding.editText5.text.toString().trim()
            val sLocation = binding.editText6.text.toString().trim()
            val userId = intent.getStringExtra("userId")

            if (sUsername.isEmpty() || sLocation.isEmpty() || imageUri == null) {
                Toast.makeText(this, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val imageRef = storage.reference.child("profileImages").child(userId ?: "").child("profile.jpg")
            imageRef.putFile(imageUri!!)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        val imageUrl = downloadUri.toString()
                        val profileData = hashMapOf(
                            "username" to sUsername,
                            "location" to sLocation,
                            "imageUrl" to imageUrl
                        )

                        db.collection("user").document(userId!!).collection("profiles").add(profileData)
                            .addOnSuccessListener {
                                // Success
                                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                                // Navigate to the next activity or perform other actions if needed
                                val intent = Intent(this, Home::class.java)
                                startActivity(intent)
                            }
                            .addOnFailureListener { profileCreationError ->
                                // Handle profile creation failure
                                Toast.makeText(this, "Error creating profile: ${profileCreationError.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener { uploadImageError ->
                    // Handle image upload failure
                    Toast.makeText(this, "Error uploading image: ${uploadImageError.message}", Toast.LENGTH_SHORT).show()
                }
        }
        binding.imageButton.apply {
            setImageURI(imageUri)
            scaleType = ImageView.ScaleType.CENTER_CROP // or FIT_CENTER
        }

    }
}

