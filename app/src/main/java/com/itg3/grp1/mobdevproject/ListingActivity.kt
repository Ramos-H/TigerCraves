package com.itg3.grp1.mobdevproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itg3.grp1.mobdevproject.data.DatabaseHelper
import com.itg3.grp1.mobdevproject.data.models.Listing

class ListingActivity: AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing)

        val dbHelper = DatabaseHelper(this)

        val user = dbHelper.users.getOne(intent.extras!!.getInt("userId"))
        val welcomeBanner: TextView = findViewById(R.id.welcomeBanner)
        welcomeBanner.text = "Welcome, ${user!!.NameFirst}!"

        val listings = dbHelper.listings.getAll()
        val adapter = ListingAdapter(listings, user.Id!!)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    fun logout(view: View)
    {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}