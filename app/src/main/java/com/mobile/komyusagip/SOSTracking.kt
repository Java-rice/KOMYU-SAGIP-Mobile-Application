package com.mobile.komyusagip

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class SOSTracking : AppCompatActivity() {

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sos_tracking)

        val userCoordinatesTextView = findViewById<TextView>(R.id.usercoordinates)


        // Initialize LocationManager and LocationListener
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                updateCoordinatesTextView("${location.latitude}, ${location.longitude}")
            }

            override fun onProviderEnabled(provider: String) {}

            override fun onProviderDisabled(provider: String) {}
        }

        // Check and request location permissions if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            } else {
                startLocationUpdates()
            }
        } else {
            startLocationUpdates()
        }

        val safeButton = findViewById<ImageButton>(R.id.safebutton)
        safeButton.setOnClickListener {
            showSafeDialog(supportFragmentManager)
        }
    }

    private fun updateCoordinatesTextView(coordinates: String) {
        val userCoordinatesTextView = findViewById<TextView>(R.id.usercoordinates)
        userCoordinatesTextView.text = coordinates
        Log.d("SOSTracking", "Location Changed: $coordinates")
    }

    private fun startLocationUpdates() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0f,
                    locationListener
                )
            }
        } else {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                locationListener
            )
        }
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

    private fun showSafeDialog(fragmentManager: FragmentManager) {
        val safeDialog = SafeDialogFragment()
        safeDialog.show(fragmentManager, "dialog_safe")
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop location updates when the activity is destroyed
        locationManager.removeUpdates(locationListener)
    }

}
