package com.mobile.komyusagip

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class EditProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val backToProfileClick = findViewById<ImageButton>(R.id.backToProfile)
        backToProfileClick.setOnClickListener {
            // Finish the current activity to go back to the previous one
            finish()
        }
        val saveProfileClick = findViewById<Button>(R.id.profileSave)
        saveProfileClick.setOnClickListener {
            finish()
        }
    }
}
