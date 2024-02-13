package com.mobile.komyusagip

import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EmergencyContacts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_emergency_contacts)

        val backToHome = findViewById<ImageButton>(R.id.return_page)
        backToHome.setOnClickListener {
            finish()
        }

        // Reference the LinearLayout where you want to add the contact containers
        val contactContainerLayout = findViewById<LinearLayout>(R.id.contactCont)

        // Assuming 'contact_container.xml' is the layout file for your contact container
        val contactContainerIds = arrayOf(
            R.layout.contact_components,
            R.layout.contact_components,
            R.layout.contact_components,
            R.layout.contact_components,
            R.layout.contact_components,
            R.layout.contact_components,
            R.layout.contact_components,
            R.layout.contact_components,
            R.layout.contact_components,
            R.layout.contact_components
            // Add more layout IDs as needed
        )

        val contactNames = arrayOf(
            "John Doe",
            "Jane Doe",
            "Bob Smith",
            "Alice Johnson",
            "David Lee",
            "Ella Martinez",
            "Michael Cruz",
            "Sophia Reyes",
            "William Garcia",
            "Olivia Rodriguez"
            // Add more contact names as needed
        )

        val contactNumbers = arrayOf(
            "+639123456789",
            "+639987654321",
            "+639555123456",
            "+639876543210",
            "+639111223344",
            "+639876543210",
            "+639222334455",
            "+639333445566",
            "+639444556677",
            "+639555667788"
            // Add more valid Philippine phone numbers as needed
        )

        for (i in contactContainerIds.indices) {
            val inflater = layoutInflater
            val contactContainer = inflater.inflate(contactContainerIds[i], null)

            // You can customize the contact container programmatically if needed

            // Set contact details
            val contactName = contactContainer.findViewById<TextView>(R.id.contactName)
            contactName.text = contactNames[i]

            val contactNumber = contactContainer.findViewById<TextView>(R.id.contactNumber)
            contactNumber.text = contactNumbers[i]

            // Add the contact container to the LinearLayout
            contactContainerLayout.addView(contactContainer)
        }
    }
}