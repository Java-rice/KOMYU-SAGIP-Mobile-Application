package com.mobile.komyusagip

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
class Profile : AppCompatActivity() {

    private lateinit var profilePicture: ImageView
    private lateinit var profileName: TextView
    private lateinit var profileUsername: TextView
    private lateinit var profileLocation: TextView

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)

        profilePicture = findViewById(R.id.ProfilePicture)
        profileName = findViewById(R.id.profileName)
        profileUsername = findViewById(R.id.ProfileUsername)
        profileLocation = findViewById(R.id.ProfileLocation)

        val editProfileClick = findViewById<Button>(R.id.editProfileNext)
        editProfileClick.setOnClickListener {
            val intent = Intent(this, EditProfile::class.java)
            startActivity(intent)
        }

        val signOutButton = findViewById<LinearLayout>(R.id.signOutLayout)
        signOutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
