package com.mobile.komyusagip

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton

class EmergencyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_emergency, container, false)

        val contactClick = view?.findViewById<Button>(R.id.emergency_contact_button)
        contactClick?.setOnClickListener {
            val intent = Intent(requireContext(), EmergencyContacts::class.java)

            startActivity(intent)
        }
        return view
    }
}