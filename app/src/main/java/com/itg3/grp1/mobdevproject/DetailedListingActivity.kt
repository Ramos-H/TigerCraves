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
import com.itg3.grp1.mobdevproject.data.models.Review
import kotlin.random.Random

class DetailedListingActivity : AppCompatActivity()
{
    var userId: Int? = -1
    // Declare titleValidationText, contentValidationText, and ratingValidationText as properties of the class
    private lateinit var titleValidationText: TextView
    private lateinit var contentValidationText: TextView
    private lateinit var ratingValidationText: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailedlisting)

        userId = intent.extras?.getInt("userId")!!
        val listingId = intent.extras?.getInt("listingId")

        Toast.makeText(this, "User ID: $userId", Toast.LENGTH_LONG).show()
        Toast.makeText(this, "Listing ID: $listingId", Toast.LENGTH_LONG).show()

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

        // Find views by their IDs
        val addReviewButton = findViewById<ImageButton>(R.id.btnAddReview)

        // Dummy review stuff
        val user = dbHelper.users.getOne(2)
        val dummyListing = dbHelper.listings.getOne(2)
        val reviews = ArrayList<Review>()
        for(index in 1..10)
        {
            reviews.add(
                Review(null, user!!, dummyListing!!,
                Random(5).nextDouble(1.0, 5.0),
                "Review $index",
                "Review content goes here. Hello Madlang People Mabuhay Mini Miss U, Mini Miss U Hello Madlang People Mabuhay Mini Miss U, Mini Miss U Hello madlang people mabuhay Our cutieful cutie queens cute little stars Review content goes here. Hello Madlang People Mabuhay Mini Miss U, Mini Miss U Hello Madlang People Mabuhay Mini Miss U, Mini Miss U Hello madlang people mabuhay Our cutieful cutie queens cute little stars")
            )
        }

        val reviewAdapter = ReviewAdapter(reviews)
        val reviewRecycler = findViewById<RecyclerView>(R.id.reviewRecycler)
        reviewRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        reviewRecycler.adapter = reviewAdapter

        // Set click listener for the "Add Review" button
        addReviewButton.setOnClickListener {
            // Show the Review Composer dialog
            showReviewComposerDialog()
        }
    }

    fun goToListingPage(view: View)
    {
        startActivity(Intent(this, ListingActivity::class.java))
    }

    // Function to show a toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Function to show the Review Composer dialog
    private fun showReviewComposerDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_review_composer, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add Review")

        val alertDialog = dialogBuilder.show()

        // Find views in the dialog by their IDs
        val ratingBar = dialogView.findViewById<RatingBar>(R.id.dialogRatingBar)
        val titleEditText = dialogView.findViewById<EditText>(R.id.dialogTitleEditText)
        val contentEditText = dialogView.findViewById<EditText>(R.id.dialogContentEditText)

        // Initialize titleValidationText, contentValidationText, and ratingValidationText
        titleValidationText = dialogView.findViewById(R.id.dialogTitleValidationText)
        contentValidationText = dialogView.findViewById(R.id.dialogContentValidationText)
        ratingValidationText = dialogView.findViewById(R.id.dialogRatingValidationText)

        val postButton = dialogView.findViewById<Button>(R.id.dialogPostButton)
        val cancelButton = dialogView.findViewById<Button>(R.id.dialogCancelButton)

        // Set initial rating to 0
        ratingBar.rating = 0.0f

        // Set click listener for the "Post" button in the dialog
        postButton.setOnClickListener {
            // Validate the input fields
            val rating = ratingBar.rating
            val title = titleEditText.text.toString().trim()
            val content = contentEditText.text.toString().trim()

            if (validateInputFields(title, content, rating, titleEditText, contentEditText)) {
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
    private fun validateInputFields(
        title: String,
        content: String,
        rating: Float,
        titleEditText: EditText,
        contentEditText: EditText
    ): Boolean {
        // Validation logic for title, content, and rating
        var isValid = true

        if (title.isEmpty()) {
            showValidationError(titleEditText, titleValidationText, "Title is required")
            isValid = false
        } else if (title.length > 1024) {
            showValidationError(titleEditText, titleValidationText, "Title cannot exceed 1024 characters")
            isValid = false
        } else {
            clearValidationErrors(titleEditText, titleValidationText)
        }

        if (content.isEmpty()) {
            showValidationError(contentEditText, contentValidationText, "Content is required")
            isValid = false
        } else if (content.length > 2048) {
            showValidationError(contentEditText, contentValidationText, "Content cannot exceed 2048 characters")
            isValid = false
        } else {
            clearValidationErrors(contentEditText, contentValidationText)
        }

        if (rating == 0.0f) {
            showValidationErrorForRating("Rating is required")
            isValid = false
        } else {
            clearValidationErrorsForRating()
        }

        return isValid
    }

    // Function to show validation error for rating
    private fun showValidationErrorForRating(message: String) {
        ratingValidationText.text = message
        ratingValidationText.visibility = View.VISIBLE
        ratingValidationText.setTextColor(resources.getColor(R.color.errorText))
    }

    // Function to clear validation errors for rating
    private fun clearValidationErrorsForRating() {
        ratingValidationText.setTextColor(resources.getColor(R.color.defaultText))
        ratingValidationText.visibility = View.INVISIBLE
    }

    // Function to show validation error
    private fun showValidationError(editText: EditText, validationText: TextView, message: String) {
        // Change text color and border color
        editText.setTextColor(resources.getColor(R.color.errorText))
        editText.backgroundTintList = resources.getColorStateList(R.color.errorBorder)

        // Show validation error message
        validationText.text = message
        validationText.visibility = View.VISIBLE
        validationText.setTextColor(resources.getColor(R.color.errorText))
    }

    // Function to clear validation errors
    private fun clearValidationErrors(editText: EditText, validationText: TextView) {
        // Reset text color, border color, and hide validation text
        editText.setTextColor(resources.getColor(R.color.defaultText))
        editText.backgroundTintList = resources.getColorStateList(R.color.defaultBorder)

        validationText.setTextColor(resources.getColor(R.color.defaultText))
        validationText.visibility = View.INVISIBLE
    }
}
