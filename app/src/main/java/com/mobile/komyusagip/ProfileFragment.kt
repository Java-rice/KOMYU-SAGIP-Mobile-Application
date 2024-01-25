package com.mobile.komyusagip

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

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
        return view
    }
}
