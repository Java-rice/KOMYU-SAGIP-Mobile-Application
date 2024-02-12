package com.mobile.komyusagip

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Find the Button in the fragment's view
        val editProfileClick = view.findViewById<Button>(R.id.editProfileNext)
        // Set click listener for the Button
        editProfileClick.setOnClickListener {
            // Start the next activity when the button is clicked
            val intent = Intent(requireContext(), EditProfile::class.java)
            startActivity(intent)
        }

        // Find the sign out LinearLayout in the fragment's view
        val signOutButton = view.findViewById<LinearLayout>(R.id.signOutLayout)
        // Set click listener for the sign out LinearLayout
        signOutButton.setOnClickListener {
            // Sign out the user
            FirebaseAuth.getInstance().signOut()
            // Start the MainActivity
            startActivity(Intent(requireContext(), MainActivity::class.java))
            // Finish the current activity
            requireActivity().finish()
        }

        return view
    }
}
