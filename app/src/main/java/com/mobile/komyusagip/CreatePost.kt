package com.mobile.komyusagip

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import java.util.Locale

class CreatePost : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createpost)

        val backToHome = findViewById<ImageButton>(R.id.backtohome)
        backToHome.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        val timeTextField = findViewById<EditText>(R.id.timeTextField)
        val timePickerButton = findViewById<Button>(R.id.timePickerButton)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                val selectedTime = formatTime(hourOfDay, minute)
                timeTextField.setText(selectedTime)
            },
            12, // Initial hour (you can set your own default)
            0,  // Initial minute (you can set your own default)
            false // Set to false for 12-hour format
        )

        timePickerButton.setOnClickListener {
            timePickerDialog.show()
        }

        timePickerButton.setOnClickListener {
            timePickerDialog.show()
        }
        val dateTextField = findViewById<EditText>(R.id.dateTextField)
        val datePickerButton = findViewById<Button>(R.id.datePickerButton)
        val calendar = Calendar.getInstance()
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, day ->
                val selectedDate = formatDate(year, month + 1, day)
                dateTextField.setText(selectedDate)
            },
            initialYear,
            initialMonth,
            initialDay
        )

        datePickerButton.setOnClickListener {
            datePickerDialog.show()
        }

        val dropdownTypeOfCrime = findViewById<Spinner>(R.id.spinnerTypeOfCrime)
        val spinnerSelectedValueTextView = findViewById<TextView>(R.id.selectedSpinnerTypeOfCrime)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.typeOfCrime,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropdownTypeOfCrime.adapter = adapter
        dropdownTypeOfCrime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
    private fun formatTime(hour: Int, minute: Int): String {
        val isAM = hour < 12
        val amPm = if (isAM) "AM" else "PM"
        val hourFormatted = if (hour % 12 == 0) 12 else hour % 12
        return String.format(Locale.getDefault(), "%02d:%02d %s", hourFormatted, minute, amPm)
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        return String.format(Locale.getDefault(), "%02d-%02d-%04d", month, day, year)
    }
    private fun showTimePickerDialog(initialHour: Int, initialMinute: Int) {
        val timePicker = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { view: TimePicker?, hourOfDay: Int, minute: Int ->
                // Do something with the selected time
                val selectedTime = "$hourOfDay:$minute"
                // You can update the UI or perform any actions with the selected time
            },
            initialHour,
            initialMinute,
            false // Set to true if you want 24-hour format
        )

        timePicker.show()
    }
    private fun validateAndSubmit() {
        val addDescription = findViewById<TextInputEditText>(R.id.addDesciption)
        val spinnerSelectedValueTextView = findViewById<TextView>(R.id.selectedSpinnerTypeOfCrime)

        val description = addDescription.text?.toString()?.trim()
        val typeOfCrime = spinnerSelectedValueTextView.text.toString()

        if (description.isNullOrEmpty()) {
            addDescription.error = "Add description"
            return
        }

        // Get current user ID
        val userId = auth.currentUser?.uid
        userId?.let { uid ->
            // Reference to the user's document
            val userRef = db.collection("user").document(uid)

            // Create a map for the post data
            val postData = hashMapOf(
                "description" to description,
                "typeOfCrime" to typeOfCrime
            )

            // Add the post to the "post" subcollection of the user's document
            userRef.collection("post")
                .add(postData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Post submitted successfully", Toast.LENGTH_SHORT).show()
                    // Redirect user to home screen or any other screen as desired
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to submit post: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } ?: run {
            // User not authenticated, handle accordingly
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }
}
