package com.mobile.komyusagip

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {
    private lateinit var profilePicture: CircleImageView
    private lateinit var profileName: TextView
    private lateinit var profileUsername: TextView
    private lateinit var profileLocation: TextView

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Find views within the inflated layout
        profilePicture = view.findViewById(R.id.ProfilePicture)
        profileName = view.findViewById(R.id.profileName)
        profileUsername = view.findViewById(R.id.ProfileUsername)
        profileLocation = view.findViewById(R.id.ProfileLocation)

        // Find the Button in the fragment's view
        val editProfileClick = view.findViewById<Button>(R.id.editProfileNext)
        // Set click listener for the Button
        editProfileClick.setOnClickListener {
            // Start the next activity when the button is clicked
            val intent = Intent(requireContext(), EditProfile::class.java)
            startActivity(intent)
        }

        val signOutButton = view.findViewById<TextView>(R.id.SignOut)
        signOutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        val userId = auth.currentUser?.uid
        userId?.let {
            db.collection("user").document(userId).get()
                .addOnSuccessListener { userDocument ->
                    if (userDocument != null) {
                        val firstName = userDocument.getString("firstName") ?: ""
                        val lastName = userDocument.getString("lastName") ?: ""
                        val name = "$firstName $lastName"
                        profileName.text = name


                        val profileRef = userDocument.reference.collection("profiles")
                        profileRef.get().addOnSuccessListener { profileQuerySnapshot ->
                            if (!profileQuerySnapshot.isEmpty) {
                                val profileDocument = profileQuerySnapshot.documents[0]
                                // Fetch data from the profile document and update UI
                                val imageUrl = profileDocument.getString("imageUrl")
                                val username = profileDocument.getString("username")
                                val location = profileDocument.getString("location")

                                // Load profile image using Picasso
                                imageUrl?.let {
                                    Picasso.get().load(it).into(profilePicture)
                                }

                                // Update TextViews with user data
                                val userWithAtSymbol = "@$username"
                                profileUsername.text = userWithAtSymbol
                                profileLocation.text = location
                            }
                        }

                    }
                }
        }

        return view
    }
}

