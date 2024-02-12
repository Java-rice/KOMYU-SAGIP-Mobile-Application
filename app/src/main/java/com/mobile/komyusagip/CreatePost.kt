package com.mobile.komyusagip

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.textfield.TextInputEditText

class CreatePost : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createpost)

        val backToHome = findViewById<ImageButton>(R.id.backtohome)
        backToHome.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        val DropdownTypeOfCrime = findViewById<Spinner>(R.id.spinnerTypeOfCrime)
        val spinnerSelectedValueTextView = findViewById<TextView>(R.id.selectedSpinnerTypeOfCrime)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.typeOfCrime,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        DropdownTypeOfCrime.adapter = adapter
        DropdownTypeOfCrime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                spinnerSelectedValueTextView.text = selectedItem
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No implementation needed
            }
        }

        val submitButton = findViewById<Button>(R.id.SubmitButton)
        submitButton.setOnClickListener {
            validateAndSubmit()
        }
    }

    private fun validateAndSubmit() {
        val addDescription = findViewById<TextInputEditText>(R.id.addDesciption)
        val spinnerSelectedValueTextView = findViewById<TextView>(R.id.selectedSpinnerTypeOfCrime)

        val descriptionAdded = addDescription.text?.isNotBlank() ?: false
        val typeOfCrimeSelected = spinnerSelectedValueTextView.text.isNotBlank()

        if (!typeOfCrimeSelected) {
            spinnerSelectedValueTextView.error = "Select type of crime"
        } else {
            spinnerSelectedValueTextView.error = null
        }

        if (!descriptionAdded) {
            addDescription.error = "Add description"
        } else {
            addDescription.error = null
        }

        if (typeOfCrimeSelected && descriptionAdded) {
            val intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
        }
    }
}