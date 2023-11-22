package com.itg3.grp1.mobdevproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itg3.grp1.mobdevproject.data.DatabaseHelper

class DetailedListingActivity : AppCompatActivity()
{
    var userId: Int? = -1
    // Declare titleValidationText, contentValidationText, and ratingValidationText as properties of the class
    private lateinit var ratingValidationText: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailedlisting)

        userId = intent.extras?.getInt("userId")!!
        val listingId = intent.extras?.getInt("listingId")

        val dbHelper = DatabaseHelper(this)
        val listing = dbHelper.listings.getOne(listingId!!)

        val tvName: TextView = findViewById(R.id.tvName)
        val tvLocation: TextView = findViewById(R.id.tvLocation)
        val tvPriceMin: TextView = findViewById(R.id.tvPriceMin)
        val tvPriceMax: TextView = findViewById(R.id.tvPriceMax)
        val tvRating: TextView = findViewById(R.id.tvRating)

        tvName.text = listing!!.Name
        tvLocation.text = listing!!.Address
        tvPriceMin.text = String.format("%.2f", listing!!.PriceMin)
        tvPriceMax.text = String.format("%.2f", listing!!.PriceMax)
        tvRating.text = String.format("%.1f", listing!!.Rating)

        val reviews = dbHelper.reviews.getAll().filter { listingId == it.Listing.Id }
        val reviewAdapter = ReviewAdapter(reviews!!)
        val reviewRecycler = findViewById<RecyclerView>(R.id.reviewRecycler)
        reviewRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        reviewRecycler.adapter = reviewAdapter
    }

    fun goToListingPage(view: View)
    {
        val intent = Intent(this, ListingActivity::class.java)
        intent.putExtra("userId", userId)
        startActivity(intent)
    }

    // Function to show a toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Function to show the Review Composer dialog
    fun showReviewComposerDialog(view: View)
    {
        val dialogView = layoutInflater.inflate(R.layout.dialog_review_composer, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add Review")

        val alertDialog = dialogBuilder.show()

        // Find views in the dialog by their IDs
        val ratingBar: RatingBar = dialogView.findViewById(R.id.dialogRatingBar)
        val fieldTitle: ValEditText = dialogView.findViewById(R.id.fieldTitle)
        val fieldContent: ValEditText = dialogView.findViewById(R.id.fieldContent)

        // Initialize titleValidationText, contentValidationText, and ratingValidationText
        ratingValidationText = dialogView.findViewById(R.id.dialogRatingValidationText)

        val postButton = dialogView.findViewById<Button>(R.id.dialogPostButton)
        val cancelButton = dialogView.findViewById<Button>(R.id.dialogCancelButton)

        // Set initial rating to 0
        ratingBar.rating = 0.0f

        // Set click listener for the "Post" button in the dialog
        postButton.setOnClickListener {
            // Validate the input fields
            val rating = ratingBar.rating
            val title = fieldTitle.text.toString().trim()
            val content = fieldContent.text.toString().trim()

            if (validateInputFields(fieldTitle, fieldContent, rating))
            {
                // Both fields are valid, hide the dialog and show a toast
                alertDialog.dismiss()
                showToast("Review Posted!")
            }
        }

        // Set click listener for the "Cancel" button in the dialog
        cancelButton.setOnClickListener {
            // Close the dialog without posting the review
            alertDialog.dismiss()
        }
    }

    // Function to validate input fields
    private fun validateInputFields(fieldTitle: ValEditText, fieldContent: ValEditText, rating: Float): Boolean
    {
        fieldTitle.error = null
        fieldContent.error = null
        clearValidationErrorsForRating()

        var title = fieldTitle.text?.trim()
        var content = fieldContent.text?.trim()

        if (title.isNullOrBlank())
        {
            fieldTitle.error = "Title is required"
        }
        else if (title.length > 1024)
        {
            fieldTitle.error = "Title cannot exceed 1024 characters"
        }

        if (content.isNullOrBlank())
        {
            fieldContent.error = "Content is required"
        }
        else if (content.length > 2048)
        {
            fieldContent.error = "Content cannot exceed 2048 characters"
        }

        val noRatingGiven = rating <= 0.0f
        if (noRatingGiven)
        {
            showValidationErrorForRating("Rating is required")
        }

        return fieldTitle.error.isNullOrBlank() && fieldContent.error.isNullOrBlank() && !noRatingGiven
    }

    // Function to show validation error for rating
    private fun showValidationErrorForRating(message: String)
    {
        ratingValidationText.text = message
        ratingValidationText.visibility = View.VISIBLE
        ratingValidationText.setTextColor(resources.getColor(R.color.errorText))
    }

    // Function to clear validation errors for rating
    private fun clearValidationErrorsForRating() {
        ratingValidationText.setTextColor(resources.getColor(R.color.defaultText))
        ratingValidationText.visibility = View.INVISIBLE
    }
}
