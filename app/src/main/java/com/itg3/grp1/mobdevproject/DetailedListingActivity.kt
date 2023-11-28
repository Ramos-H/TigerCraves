package com.itg3.grp1.mobdevproject

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itg3.grp1.mobdevproject.data.DatabaseHelper
import com.itg3.grp1.mobdevproject.data.models.Listing
import com.itg3.grp1.mobdevproject.data.models.Review

class DetailedListingActivity : AppCompatActivity() {
    private var userId: Int? = -1
    private val dbHelper = DatabaseHelper(this)
    private var listing: Listing? = null

    private var reviewAdapter: ReviewAdapter? = null

    private lateinit var ratingValidationText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
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


        if (!reviews.isNullOrEmpty()) {
            val noReviewsText: TextView = findViewById(R.id.noReviewsText)
            noReviewsText.visibility = View.GONE
            initReviewAdapter(reviews)
        }
    }

    private fun initReviewAdapter(reviews: List<Review>) {
        val reviewRecycler = findViewById<RecyclerView>(R.id.reviewRecycler)
        reviewRecycler.visibility = View.VISIBLE
        reviewRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        if (reviewAdapter != null) {
            reviewAdapter!!.dataset = reviews
            reviewAdapter!!.notifyDataSetChanged()
        } else {
            reviewAdapter = ReviewAdapter(reviews)
            reviewRecycler.adapter = reviewAdapter
        }
    }

    fun goToListingPage(view: View) {
        val intent = Intent(this, ListingActivity::class.java)
        intent.putExtra("userId", userId)
        startActivity(intent)
    }

    // Function to show a toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Function to show the Review Composer dialog
    fun showReviewComposerDialog(view: View) {
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

            if (validateInputFields(fieldTitle, fieldContent, rating)) {
                // Both fields are valid, hide the dialog and show a toast
                val poster = dbHelper.users.getOne(userId!!)
                val newReview = Review(null, poster!!, listing!!, rating.toDouble(), title, content)
                val newReviewId = dbHelper.reviews.add(newReview)

                if (newReviewId.toInt() == -1) {
                    showToast("Review cannot be posted. There must be a problem with the database.")
                } else {
                    // Get fresh list of reviews
                    val reviews = dbHelper.reviews.getAll().filter { listing!!.Id == it.Listing.Id }

                    // Update average rating for the current listing
                    val averageRating = reviews.sumOf { it.Rating } / reviews.count()
                    listing!!.Rating = averageRating
                    dbHelper.listings.update(listing!!)

                    initReviewAdapter(reviews)
                    showToast("Review Posted!")
                }

                alertDialog.dismiss()
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
        fieldTitle: ValEditText,
        fieldContent: ValEditText,
        rating: Float
    ): Boolean {
        fieldTitle.error = null
        fieldContent.error = null
        clearValidationErrorsForRating()

        var title = fieldTitle.text?.trim()
        var content = fieldContent.text?.trim()

        if (title.isNullOrBlank()) {
            fieldTitle.error = "Title is required"
        } else if (title.length > 1024) {
            fieldTitle.error = "Title cannot exceed 1024 characters"
        }

        if (content.isNullOrBlank()) {
            fieldContent.error = "Content is required"
        } else if (content.length > 2048) {
            fieldContent.error = "Content cannot exceed 2048 characters"
        }

        val noRatingGiven = rating <= 0.0f
        if (noRatingGiven) {
            showValidationErrorForRating("Rating is required")
        }

        return fieldTitle.error.isNullOrBlank() && fieldContent.error.isNullOrBlank() && !noRatingGiven
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

    /* FUNCTIONS FOR EDIT AND DELETE YOUR REVIEW SECTION */

    // Function to show the edit dialog
    fun showEditDialog(view: View) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_review, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Edit Review")

        val editTitle = dialogView.findViewById<ValEditText>(R.id.editTitle)
        val editContent = dialogView.findViewById<ValEditText>(R.id.editContent)
        val saveChangesButton = dialogView.findViewById<ImageButton>(R.id.saveChangesButton)
        val cancelEditButton = dialogView.findViewById<ImageButton>(R.id.cancelEditButton)

        // Set existing review details to EditText fields if needed
        // ...

        val alertDialog = dialogBuilder.show()

        // Set click listener for "Save Changes" button
        saveChangesButton.setOnClickListener {
            // Save changes logic goes here
            showToast("Review Edited!")
            // Refresh the UI or handle other actions after editing...
            alertDialog.dismiss()
        }

        // Set click listener for "Cancel" button
        cancelEditButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    // Function to show the delete confirmation dialog
    fun showDeleteConfirmationDialog(view: View) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_delete_confirmation)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val confirmDeleteButton = dialog.findViewById<ImageButton>(R.id.confirmDeleteButton)
        val cancelDeleteButton = dialog.findViewById<ImageButton>(R.id.cancelDeleteButton)

        // Set click listener for "Confirm" button
        confirmDeleteButton.setOnClickListener {
            // Delete the review logic goes here
            showToast("Review Deleted!")
            // Refresh the UI or handle other actions after deletion...
            dialog.dismiss()
        }

        // Set click listener for "Cancel" button
        cancelDeleteButton.setOnClickListener {
            dialog.dismiss()
        }

        val alertDialog = dialog.show()
    }

    // Function to handle click events on editPostButton
    fun onEditPostButtonClick(view: View) {
        showEditDialog(view)
    }

    // Function to handle click events on deletePostButton
    fun onDeletePostButtonClick(view: View) {
        showDeleteConfirmationDialog(view)
    }

}