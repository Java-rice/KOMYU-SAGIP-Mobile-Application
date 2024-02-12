package com.mobile.komyusagip

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.os.CountDownTimer
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
class EmergencyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_emergency, container, false)

        val contactClick = view?.findViewById<Button>(R.id.viewContact)
        contactClick?.setOnClickListener {
            val intent = Intent(requireContext(), EmergencyContacts::class.java)

            startActivity(intent)
        }
        val hotlineClick = view?.findViewById<Button>(R.id.viewHotline)
        hotlineClick?.setOnClickListener {
            val intent = Intent(requireContext(), EmergencyHotline::class.java)

            startActivity(intent)
        }

        val sosButton = view?.findViewById<ImageButton>(R.id.SOSbutton)
        sosButton?.setOnClickListener {
            showCountdownDialog(requireActivity().supportFragmentManager)
        }


        return view
    }

    private fun showCountdownDialog(fragmentManager: FragmentManager) {
        val countdownDialog = CountdownDialogFragment()
        countdownDialog.show(fragmentManager, "countdown_dialog")
    }
    //countdown dialogue fragment
    class CountdownDialogFragment : DialogFragment() {

        private lateinit var countdownTextView: TextView

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.dialog_countdown, container, false)
            countdownTextView = view.findViewById(R.id.countdownTextView)

            val proceedButton = view.findViewById<Button>(R.id.procButton)
            proceedButton.setOnClickListener {
                // Handle the Proceed button click
                val intent = Intent(requireContext(), SOSTracking::class.java)
                startActivity(intent)
                // Add your logic for proceeding
            }

            val cancelButton = view.findViewById<Button>(R.id.canButton)
            cancelButton.setOnClickListener {
                // Handle the Cancel button click
                dismiss()
                // Add your logic for canceling
            }

            startCountdown()
            return view
        }

        private fun startCountdown() {
            object : CountDownTimer(16000, 1000) { // 16 seconds, counting every second
                override fun onTick(millisUntilFinished: Long) {
                    val secondsRemaining = millisUntilFinished / 1000
                    countdownTextView.text = secondsRemaining.toString()
                }

                override fun onFinish() {
                    dismiss() // Close the dialog when countdown finishes
                }
            }.start()
        }
    }
}