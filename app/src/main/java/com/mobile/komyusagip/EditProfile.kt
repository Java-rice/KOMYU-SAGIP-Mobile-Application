package com.mobile.komyusagip

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView

class EditProfile : AppCompatActivity() {
    private lateinit var profilePicture: CircleImageView
    private lateinit var profileName: TextView
    private lateinit var profileUsername: TextView
    private lateinit var profileLocation: TextView
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private var imageUrl: String? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            selectedImageUri?.let {
                // Handle the selected image URI here
                uploadImageToStorage(selectedImageUri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        profilePicture = findViewById(R.id.uploadImage)
        profilePicture.setOnClickListener {
            // Open gallery to select an image
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            getContent.launch(intent)
        }

        val backToProfileClick = findViewById<ImageButton>(R.id.backToProfile)
        backToProfileClick.setOnClickListener {
            finish()
        }

        val saveProfileClick = findViewById<Button>(R.id.profileSave)
        saveProfileClick.setOnClickListener {
            validateAndSubmit()
        }
    }

    private fun uploadImageToStorage(imageUri: Uri) {
        val userId = auth.currentUser?.uid
        userId?.let {
            val storageRef = storage.reference.child("profileImages").child(userId).child("profile.jpg")
            val uploadTask = storageRef.putFile(imageUri)
            uploadTask.addOnSuccessListener { taskSnapshot ->
                val downloadUrlTask = taskSnapshot.storage.downloadUrl
                downloadUrlTask.addOnSuccessListener { uri ->
                    imageUrl = uri.toString()
                    profilePicture.setImageURI(imageUri)
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to upload image: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to upload image: ${it.message}", Toast.LENGTH_SHORT).show()
            }
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

                        // Update the fields in the profile document including imageUrl
                        val updateMap = mutableMapOf<String, Any>(
                            "username" to username,
                            "location" to location
                        )
                        imageUrl?.let { updateMap["imageUrl"] = it }

                        profileDocument.reference.update(updateMap)
                            .addOnSuccessListener {
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
