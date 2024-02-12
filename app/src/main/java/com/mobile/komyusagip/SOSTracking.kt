package com.mobile.komyusagip

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class SOSTracking : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sos_tracking)

        val safeButton = findViewById<ImageButton>(R.id.safebutton)
        safeButton.setOnClickListener {
            showSafeDialog(supportFragmentManager)
        }
    }

    private fun showSafeDialog(fragmentManager: FragmentManager) {
        val safeDialog = SafeDialogFragment()
        safeDialog.show(fragmentManager, "dialog_safe")
    }

    class SafeDialogFragment : DialogFragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.dialog_safe, container, false)

            val proceedButton = view.findViewById<Button>(R.id.procSButton)
            proceedButton.setOnClickListener {
                // Handle the Proceed button click
                val intent = Intent(requireContext(), Home::class.java)
                startActivity(intent)
                // Add your logic for proceeding
            }

            val cancelButton = view.findViewById<Button>(R.id.canSButton)
            cancelButton.setOnClickListener {
                // Handle the Cancel button click
                dismiss()
                // Add your logic for canceling
            }
            return view
        }
    }
}