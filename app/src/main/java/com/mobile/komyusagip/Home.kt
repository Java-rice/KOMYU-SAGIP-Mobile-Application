package com.mobile.komyusagip

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mobile.komyusagip.databinding.ActivityHomeBinding


class Home  : AppCompatActivity(){
    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.background = null
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.bottom_home -> openFragment(HomeFragment())
                R.id.bottom_emergency -> openFragment(EmergencyFragment())
                R.id.bottom_profile -> openFragment(ProfileFragment())
                R.id.bottom_statistics -> openFragment(ReportsFragment())
            }
            true
        }
        fragmentManager = supportFragmentManager
        openFragment(HomeFragment())

        val fab: FloatingActionButton = findViewById(R.id.fab)
        binding.fab.setOnClickListener{
            val intent = Intent(this, CreatePost::class.java)
            startActivity(intent)
        }
    }
    private fun openFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}