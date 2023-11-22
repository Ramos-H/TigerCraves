package com.itg3.grp1.mobdevproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itg3.grp1.mobdevproject.models.Listing

class ListingActivity: AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = DatabaseHelper(this)
        val user = dbHelper.users.getOne(2)

        val listings = ArrayList<Listing>()
        for (index in 1..10)
        {
            listings.add(Listing(
                null,
                user!!,
                "Restaurant $index",
                "UST",
                10.0,
                1000.0,
                5.0
            ))
        }

        val adapter = ListingAdapter(listings)

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