package com.itg3.grp1.mobdevproject

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailedListingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailedlisting)

        // Find views by their IDs
        val backButton = findViewById<ImageButton>(R.id.btnBackToListings)

        // Set click listener for the back button
        backButton.setOnClickListener {
            // Show a toast message when clicked
            showToast("Navigated to Listings Page")
        }
    }

    // Function to show a toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
