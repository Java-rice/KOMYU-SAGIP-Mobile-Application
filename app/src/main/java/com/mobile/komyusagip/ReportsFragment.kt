package com.mobile.komyusagip

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner


class ReportsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reports, container, false)

        // Sample data for location and type of crime
        val locations = arrayOf("Location 1", "Location 2", "Location 3")
        val crimeTypes = arrayOf("Type 1", "Type 2", "Type 3")

        // ArrayAdapter for populating the Spinners
        val locationAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, locations)
        val crimeTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, crimeTypes)

        // Specify the layout style for the drop-down choices
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        crimeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set the adapters for the Spinners
        val locationSpinner: Spinner = view.findViewById(R.id.locationSpinner)
        val crimeTypeSpinner: Spinner = view.findViewById(R.id.crimeTypeSpinner)

        locationSpinner.adapter = locationAdapter
        crimeTypeSpinner.adapter = crimeTypeAdapter

        return view
    }
}