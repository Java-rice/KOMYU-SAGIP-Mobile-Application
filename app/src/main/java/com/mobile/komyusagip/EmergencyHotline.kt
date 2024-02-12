package com.mobile.komyusagip

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EmergencyHotline : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_emergency_hotlines)

        val backToHome = findViewById<ImageButton>(R.id.returnh_page)
        backToHome.setOnClickListener {
            finish()
        }

        // Reference the LinearLayout where you want to add the hotline containers
        val hotlineContainerLayout = findViewById<LinearLayout>(R.id.hotlineCont)

        // Assuming 'hotline_components.xml' is the layout file for your hotline container
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
            R.layout.hotline_components
            // Add more layout IDs as needed
        )

        val hotlineNames = arrayOf(
            "Philippine Coast Guard",
            "Philippine National Police",
            "Bureau of Fire Protection",
            "National Disaster Risk Reduction and Management Council",
            "Red Cross Philippines",
            "Philippine General Hospital",
            "National Poison Management and Control Center",
            "National Center for Mental Health",
            "Department of Health Hotline",
            "Emergency Medical Services"
            // Add more hotline names as needed
        )

        val hotlineNumbers = arrayOf(
            "(02) 527-8481",
            "117",
            "117 or (02) 729-5166",
            "911 or (02) 8911-1406",
            "143, (02) 790-2300, or (02) 790-2383",
            "(02) 554-8400 local 3044",
            "(02) 8403-5673 or 0922-818-2199",
            "(02) 7989-USAP or (02) 7989-8727",
            "1555 or (02) 894-COVID",
            "16911 or 0917-899-8956"
            // Add more valid Philippine phone numbers as needed
        )

        for (i in hotlineContainerIds.indices) {
            val inflater = layoutInflater
            val hotlineContainer = inflater.inflate(hotlineContainerIds[i], null)

            // You can customize the hotline container programmatically if needed

            // Set hotline details
            val hotlineName = hotlineContainer.findViewById<TextView>(R.id.contactName)
            hotlineName.text = hotlineNames[i]

            val hotlineNumber = hotlineContainer.findViewById<TextView>(R.id.contactNumber)
            hotlineNumber.text = hotlineNumbers[i]

            val callButton = hotlineContainer.findViewById<View>(R.id.loginAuth)
            callButton.setOnClickListener {
                dialPhoneNumber(hotlineNumbers[i])
            }

            // Add the hotline container to the LinearLayout
            hotlineContainerLayout.addView(hotlineContainer)
        }

    }
    private fun dialPhoneNumber(phoneNumber: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:$phoneNumber")
        if (dialIntent.resolveActivity(packageManager) != null) {
            startActivity(dialIntent)
        }
    }
}