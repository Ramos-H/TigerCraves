package com.itg3.grp1.mobdevproject

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itg3.grp1.mobdevproject.data.DatabaseHelper
import com.itg3.grp1.mobdevproject.data.models.Listing
import com.itg3.grp1.mobdevproject.data.models.Review

class DetailedListingActivity : AppCompatActivity()
{
    private var userId: Int? = -1
    private val dbHelper = DatabaseHelper(this)
    private var listing: Listing? = null

    private var reviewAdapter: ReviewAdapter? = null

    private lateinit var ratingValidationText: TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailedlisting)

        userId = intent.extras?.getInt("userId")!!
        val listingId = intent.extras?.getInt("listingId")

        listing = dbHelper.listings.getOne(listingId!!)

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


        if(!reviews.isNullOrEmpty())
        {
            val noReviewsText: TextView = findViewById(R.id.noReviewsText)
            noReviewsText.visibility = View.GONE
            initReviewAdapter(reviews)
        }
    }

    private fun initReviewAdapter(reviews: List<Review>)
    {
        val reviewRecycler = findViewById<RecyclerView>(R.id.reviewRecycler)
        reviewRecycler.visibility = View.VISIBLE
        reviewRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        if(reviewAdapter != null)
        {
            reviewAdapter!!.dataset = reviews
            reviewAdapter!!.notifyDataSetChanged()
        }
        else
        {
            reviewAdapter = ReviewAdapter(reviews)
            reviewRecycler.adapter = reviewAdapter
        }
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
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_review_composer)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Find views in the dialog by their IDs
        val ratingBar: RatingBar = dialog.findViewById(R.id.dialogRatingBar)
        val fieldTitle: ValEditText = dialog.findViewById(R.id.fieldTitle)
        val fieldContent: ValEditText = dialog.findViewById(R.id.fieldContent)

        // Initialize titleValidationText, contentValidationText, and ratingValidationText
        ratingValidationText = dialog.findViewById(R.id.dialogRatingValidationText)

        val postButton = dialog.findViewById<ImageButton>(R.id.dialogPostButton)
        val cancelButton = dialog.findViewById<ImageButton>(R.id.dialogCancelButton)

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
                val poster = dbHelper.users.getOne(userId!!)
                val newReview = Review(null, poster!!, listing!!, rating.toDouble(), title, content)
                val newReviewId = dbHelper.reviews.add(newReview)

                if(newReviewId.toInt() == -1)
                {
                    showToast("Review cannot be posted. There must be a problem with the database.")
                }
                else
                {
                    // Get fresh list of reviews
                    val reviews = dbHelper.reviews.getAll().filter { listing!!.Id == it.Listing.Id }

                    // Update average rating for the current listing
                    val averageRating = reviews.sumOf { it.Rating } / reviews.count()
                    listing!!.Rating = averageRating
                    dbHelper.listings.update(listing!!)

                    initReviewAdapter(reviews)
                    showToast("Review Posted!")
                }

                dialog.dismiss()
            }
        }

        // Set click listener for the "Cancel" button in the dialog
        cancelButton.setOnClickListener {
            // Close the dialog without posting the review
            dialog.dismiss()
        }

        dialog.show()
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
