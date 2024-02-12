package com.mobile.komyusagip

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter;
import android.widget.Spinner;

class CreatePost : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createpost)

        val backToHome = findViewById<ImageButton>(R.id.backtohome4)
        backToHome.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        val spinnerLanguages = findViewById<Spinner>(R.id.spinnerTypeOfCrime)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.typeOfCrime,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLanguages.adapter = adapter
    }
}