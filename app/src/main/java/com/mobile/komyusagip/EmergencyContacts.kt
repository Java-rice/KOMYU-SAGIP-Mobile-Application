package com.mobile.komyusagip
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class EmergencyContacts : AppCompatActivity()  {
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
            R.layout.contact_components,
            R.layout.contact_components,
            R.layout.contact_components,
            R.layout.contact_components
        )

        for (contactContainerId in contactContainerIds) {
            val inflater = layoutInflater
            val contactContainer = inflater.inflate(contactContainerId, null)

            // You can customize the contact container programmatically if needed

            // Add the contact container to the LinearLayout
            contactContainerLayout.addView(contactContainer)
        }
    }
}