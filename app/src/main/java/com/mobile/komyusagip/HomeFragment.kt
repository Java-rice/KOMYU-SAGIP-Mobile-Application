package com.mobile.komyusagip

import CrimeReport
import CrimeReportAdapter
import android.content.ContentValues.TAG

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CrimeReportAdapter

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

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
        // Get the current user's ID
        val userId = auth.currentUser?.uid

        // Check if the user is authenticated
        userId?.let { uid ->
            // Reference to the user's "post" subcollection
            val userPostRef = db.collection("user").document(uid).collection("post")

            // Query Firestore to fetch crime reports in the user's area
            userPostRef
                .get()
                .addOnSuccessListener { documents ->
                    val crimeReports = mutableListOf<CrimeReport>()

                    for (document in documents) {
                        val typeOfCrime = document.getString("typeOfCrime")
                        val description = document.getString("description")

                        typeOfCrime?.let { crimeType ->
                            description?.let { desc ->
                                val crimeReport = CrimeReport(crimeType, desc)
                                crimeReports.add(crimeReport)
                            }
                        }
                    }

                    // Set data to the adapter after populating the crimeReports list
                    adapter.setData(crimeReports)
                }
                .addOnFailureListener { exception ->
                    // Handle any errors
                    Log.e(TAG, "Error fetching crime reports: $exception")
                }
        }
    }

    private fun loadInYourAreaCrimeReports() {
        // Get the current user's ID
        val userId = auth.currentUser?.uid

        // Check if the user is authenticated
        userId?.let { uid ->
            // Reference to the user's "post" subcollection
            val userPostRef = db.collection("user").document(uid).collection("post")
            val userProfileRef = db.collection("user").document(uid).collection("profiles")

            // Query Firestore to fetch crime reports in the user's area
            userPostRef.get().addOnSuccessListener { documents ->
                val crimeReports = mutableListOf<CrimeReport>()

                // Fetch user profile data
                val profileRef = db.collection("user").document(uid).collection("profiles")
                profileRef.get().addOnSuccessListener { profileQuerySnapshot ->
                    if (!profileQuerySnapshot.isEmpty) {
                        val profileDocument = profileQuerySnapshot.documents[0]
                        // Fetch data from the profile document
                        val imageUrl = profileDocument.getString("imageUrl")
                        val username = profileDocument.getString("username")

                        // Process each crime report document
                        for (document in documents) {
                            val typeOfCrime = document.getString("typeOfCrime")
                            val description = document.getString("description")

                            typeOfCrime?.let { crimeType ->
                                description?.let { desc ->
                                    val crimeReport = CrimeReport(crimeType, desc)
                                    crimeReports.add(crimeReport)
                                }
                            }
                        }

                        // Update profile UI
                        updateProfileUI(requireView(), username, imageUrl)

                        // Set data to the adapter after populating the crimeReports list
                        adapter.setData(crimeReports)
                    }
                }.addOnFailureListener { exception ->
                    // Handle any errors
                    Log.e(TAG, "Error fetching profile data: $exception")
                }
            }.addOnFailureListener { exception ->
                // Handle any errors
                Log.e(TAG, "Error fetching crime reports: $exception")
            }
        }
    }

    private fun updateProfileUI(view: View, username: String?, imageUrl: String?) {
        // Update profile picture
        val profilePicture = view.findViewById<ImageView>(R.id.ProfilePicture)
        val profileUsername = view.findViewById<TextView>(R.id.ProfileUsername)

        imageUrl?.let {
            Picasso.get().load(it).into(profilePicture)
        }
        // Update username
        val userWithAtSymbol = "@$username"
        profileUsername?.text = userWithAtSymbol
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
