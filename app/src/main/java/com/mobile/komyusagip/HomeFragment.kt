package com.mobile.komyusagip

import CrimeReport
import CrimeReportAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CrimeReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.RecyclerViewFeed)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        // Set the MostRecent button as selected by default
        view.findViewById<View>(R.id.MostRecent).isSelected = true

        // Load most recent crime reports by default
        loadMostRecentCrimeReports()

        // Set click listeners for sorting buttons
        view.findViewById<View>(R.id.MostRecent).setOnClickListener {
            // Set the selected state for the MostRecent button
            it.isSelected = true
            // Reset the selected state for the other buttons
            view.findViewById<View>(R.id.InYourArea).isSelected = false
            view.findViewById<View>(R.id.CommunityWatch).isSelected = false

            loadMostRecentCrimeReports()
        }
        view.findViewById<View>(R.id.InYourArea).setOnClickListener {
            loadInYourAreaCrimeReports()
            it.isSelected = true

            view.findViewById<View>(R.id.MostRecent).isSelected = false
            view.findViewById<View>(R.id.CommunityWatch).isSelected = false
        }
        view.findViewById<View>(R.id.CommunityWatch).setOnClickListener {
            loadCommunityWatchCrimeReports()
            it.isSelected = true

            view.findViewById<View>(R.id.InYourArea).isSelected = false
            view.findViewById<View>(R.id.MostRecent).isSelected = false
        }
    }


    private fun setupRecyclerView() {
        adapter = CrimeReportAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun loadMostRecentCrimeReports() {
        // Implement logic to load most recent crime reports
        val mostRecentCrimeReports = listOf(
            CrimeReport("Theft", "Description of theft incident"),
            CrimeReport("Assault", "Description of assault incident")
            // Add more crime reports as needed
        )
        adapter.setData(mostRecentCrimeReports)
    }

    private fun loadInYourAreaCrimeReports() {
        // Implement logic to load crime reports in user's area
        val inYourAreaCrimeReports = listOf(
            CrimeReport("Vandalism", "Description of vandalism incident"),
            CrimeReport("Burglary", "Description of burglary incident")
            // Add more crime reports as needed
        )
        adapter.setData(inYourAreaCrimeReports)
    }

    private fun loadCommunityWatchCrimeReports() {
        // Implement logic to load crime reports for community watch
        val communityWatchCrimeReports = listOf(
            CrimeReport("Drug Trafficking", "Description of drug trafficking incident"),
            CrimeReport("Hit and Run", "Description of hit and run incident")
            // Add more crime reports as needed
        )
        adapter.setData(communityWatchCrimeReports)
    }
}
