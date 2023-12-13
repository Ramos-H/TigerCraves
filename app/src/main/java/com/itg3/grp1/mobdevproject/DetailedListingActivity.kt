package com.itg3.grp1.mobdevproject

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.itg3.grp1.mobdevproject.data.DatabaseHelper
import com.itg3.grp1.mobdevproject.data.models.Listing
import com.itg3.grp1.mobdevproject.data.models.Review


class DetailedListingActivity : AppCompatActivity() {
    private var userId: Int? = -1
    private var listingId: Int? = -1
    private val dbHelper = DatabaseHelper(this)
    private var listing: Listing? = null
    private var yourReview: Review? = null
    private var reviews : List<Review>? = null

    private var reviewAdapter: ReviewAdapter? = null

    private lateinit var tvName: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvPriceMin: TextView
    private lateinit var tvPriceMax: TextView
    private lateinit var tvRating: TextView
    private lateinit var ratingValidationText: TextView

    private lateinit var vYourReviewSection : View
    private lateinit var tvYourTitle: TextView
    private lateinit var tvYourRating: TextView
    private lateinit var tvYourDatePosted: TextView
    private lateinit var tvYourContent: TextView

    private lateinit var btnAddReview : ImageButton

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailedlisting)

        tvName = findViewById(R.id.tvName)
        tvLocation = findViewById(R.id.tvLocation)
        tvPriceMin = findViewById(R.id.tvPriceMin)
        tvPriceMax = findViewById(R.id.tvPriceMax)
        tvRating = findViewById(R.id.tvRating)

        tvYourTitle = findViewById(R.id.yourTitle)
        tvYourRating = findViewById(R.id.yourRating)
        tvYourDatePosted = findViewById(R.id.yourDatePosted)
        tvYourContent = findViewById(R.id.yourContent)
        vYourReviewSection = findViewById(R.id.yourReviewCard)

        btnAddReview = findViewById(R.id.btnAddReview)

        loadPageData()
        //Image Slider
        val imageSlider = findViewById<ImageSlider>(R.id.imageSlider)

        val images = dbHelper.images.getAll().filter { listing!!.Id == it.ListingId }
        val slides = images.map { SlideModel(it.ResourceId) }

        imageSlider.setImageList(slides, ScaleTypes.FIT)
    }

    private fun loadPageData()
    {
        // Fetch relevant data
        userId = intent.extras?.getInt("userId")
        listingId = intent.extras?.getInt("listingId")
        listing = dbHelper.listings.getOne(listingId!!)
        reviews = dbHelper.reviews.getAll().filter { listingId == it.Listing.Id }

        // Load fetched data into the view
        loadListingInfo()
        loadOwnReview()
        loadReviewSection()
    }

    private fun loadListingInfo()
    {
        tvName.text = listing!!.Name
        tvLocation.text = listing!!.Address
        tvPriceMin.text = String.format("%.2f", listing!!.PriceMin)
        tvPriceMax.text = String.format("%.2f", listing!!.PriceMax)
        tvRating.text = String.format("%.1f", listing!!.Rating)
    }

    private fun loadOwnReview()
    {
        yourReview = reviews?.filter { it.Poster.Id == userId }?.firstOrNull()
        if(yourReview == null)
        {
            vYourReviewSection.visibility = View.GONE
            btnAddReview.visibility = View.VISIBLE
            return
        }
        else
        {
            btnAddReview.visibility = View.GONE
            vYourReviewSection.visibility = View.VISIBLE

            tvYourTitle.text = yourReview!!.Title
            tvYourRating.text = String.format("%.1f", yourReview!!.Rating)
            tvYourDatePosted.text = yourReview!!.DatePosted.toString()
            tvYourContent.text = yourReview!!.Content
        }
    }

    private fun loadReviewSection()
    {
        val otherReviews = reviews!!.filter { it.Poster.Id != userId }
        val noReviewsText: TextView = findViewById(R.id.noReviewsText)
        val reviewRecycler = findViewById<RecyclerView>(R.id.reviewRecycler)
        if (otherReviews.isNullOrEmpty())
        {
            noReviewsText.visibility = View.VISIBLE
            reviewRecycler.visibility = View.GONE
            return
        }

        noReviewsText.visibility = View.GONE
        reviewRecycler.visibility = View.VISIBLE

        reviewRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        if (reviewAdapter != null)
        {
            reviewAdapter!!.dataset = otherReviews!!
            reviewAdapter!!.notifyDataSetChanged()
        }
        else
        {
            reviewAdapter = ReviewAdapter(otherReviews!!)
            reviewRecycler.adapter = reviewAdapter
        }
    }

    fun openMapsLink(view: View)
    {
        if(listing != null)
        {
            if(listing!!.GMapLink != null)
            {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(listing!!.GMapLink)
                )
                startActivity(intent)
            }
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
    fun showReviewComposerDialog(view: View)
    {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_review_composer)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Find views in the dialog by their IDs
        val ratingBar: RatingBar = dialog.findViewById(R.id.editRating)
        val fieldTitle: ValEditText = dialog.findViewById(R.id.fieldTitle)
        val fieldContent: ValEditText = dialog.findViewById(R.id.fieldContent)

        // Initialize titleValidationText, contentValidationText, and ratingValidationText
        ratingValidationText = dialog.findViewById(R.id.editRatingValidationText)

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

            if (validateInputFields(fieldTitle, fieldContent, ratingValidationText, rating)) {
                // Both fields are valid, hide the dialog and show a toast
                val poster = dbHelper.users.getOne(userId!!)
                val newReview = Review(null, poster!!, listing!!, rating.toDouble(), title, content)
                val newReviewId = dbHelper.reviews.add(newReview)

                if (newReviewId.toInt() == -1)
                {
                    showToast("Review cannot be posted. There must be a problem with the database.")
                }
                else
                {
                    loadPageData()
                    showToast("Review Posted!")
                    dialog.dismiss()
                }
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
    private fun validateInputFields(fieldTitle: ValEditText,
                                    fieldContent: ValEditText,
                                    ratingValText: TextView,
                                    rating: Float): Boolean
    {
        fieldTitle.error = null
        fieldContent.error = null

        ratingValText.setTextColor(resources.getColor(R.color.defaultText))
        ratingValText.visibility = View.INVISIBLE

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
            ratingValText.text = "Rating is required"
            ratingValText.visibility = View.VISIBLE
            ratingValText.setTextColor(resources.getColor(R.color.errorText))
        }

        return fieldTitle.error.isNullOrBlank() && fieldContent.error.isNullOrBlank() && !noRatingGiven
    }

    /* FUNCTIONS FOR EDIT AND DELETE YOUR REVIEW SECTION */

    // Function to show the edit dialog
    fun showEditDialog(view: View) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_edit_review)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val editTitle = dialog.findViewById<ValEditText>(R.id.editTitle)
        val editContent = dialog.findViewById<ValEditText>(R.id.editContent)
        val editRating = dialog.findViewById<RatingBar>(R.id.editRating)
        val saveChangesButton = dialog.findViewById<ImageButton>(R.id.saveChangesButton)
        val cancelEditButton = dialog.findViewById<ImageButton>(R.id.cancelEditButton)
        val editRatingValidationText = dialog.findViewById<TextView>(R.id.editRatingValidationText)

        editTitle.text = yourReview!!.Title
        editContent.text = yourReview!!.Content
        editRating.rating = yourReview!!.Rating.toFloat()

        // Set click listener for "Save Changes" button
        saveChangesButton.setOnClickListener {
            if(validateInputFields(editTitle!!, editContent, editRatingValidationText, editRating.rating))
            {
                yourReview?.Title = editTitle.text!!
                yourReview?.Content = editContent.text!!
                yourReview?.Rating = editRating.rating.toDouble()
                dbHelper.reviews.update(yourReview!!)
                loadPageData()
                showToast("Edited review")
                dialog.dismiss()
            }
        }

        // Set click listener for "Cancel" button
        cancelEditButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
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
            dbHelper.reviews.delete(yourReview!!)
            loadPageData()
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