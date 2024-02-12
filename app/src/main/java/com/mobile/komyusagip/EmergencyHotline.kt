package com.mobile.komyusagip
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class EmergencyHotline : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_emergency_hotlines)

        val backToHome = findViewById<ImageButton>(R.id.returnh_page)
        backToHome.setOnClickListener {
            finish()
        }
        // Reference the LinearLayout where you want to add the contact containers
        val hotlineContainerLayout = findViewById<LinearLayout>(R.id.hotlineCont)

        // Assuming 'contact_container.xml' is the layout file for your contact container
        val hotlineContainerIds = arrayOf(
            R.layout.hotline_components,
            R.layout.hotline_components,
            R.layout.hotline_components,
            R.layout.hotline_components,
            R.layout.hotline_components,
            R.layout.hotline_components,
            R.layout.hotline_components,
            R.layout.hotline_components,
            R.layout.hotline_components,
            R.layout.hotline_components,
            R.layout.hotline_components,
            R.layout.hotline_components,
            R.layout.hotline_components
        )

        for (contactContainerId in hotlineContainerIds) {
            val inflater = layoutInflater
            val contactContainer = inflater.inflate(contactContainerId, null)

            // You can customize the contact container programmatically if needed

            // Add the contact container to the LinearLayout
            hotlineContainerLayout.addView(contactContainer)
        }
    }
}