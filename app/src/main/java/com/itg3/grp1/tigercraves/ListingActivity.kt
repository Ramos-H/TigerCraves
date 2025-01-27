package com.itg3.grp1.tigercraves

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itg3.grp1.tigercraves.data.DatabaseHelper
import com.itg3.grp1.tigercraves.data.models.Listing
import com.itg3.grp1.tigercraves.data.models.User

class ListingActivity: AppCompatActivity()
{
    private lateinit var user : User
    private val dbHelper = DatabaseHelper(this)

    // Search
    private var searchString : String? = null

    // Sorting
    private var selectedSortCriteriaControl: Int = R.id.rb2
    private var selectedSortDirectionControl: Int = R.id.rbf1
    private var selectedSortCriteria : SortCriteria = SortCriteria.AVERAGE_RATING
    private var selectedSortDirection : SortDirections = SortDirections.DESCENDING
    private lateinit var dialogSort : Dialog

    // Filtering
    private var filterPriceMin : Double? = null
    private var filterPriceMax : Double? = null
    private var filterAveRating : Double? = null
    private lateinit var dialogFilter : Dialog

    enum class SortDirections
    {
        ASCENDING,
        DESCENDING
    }

    enum class SortCriteria
    {
        NAME,
        AVERAGE_RATING,
        PRICE
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing)

        user = dbHelper.users.getOne(intent.extras!!.getInt("userId"))!!
        val welcomeBanner: TextView = findViewById(R.id.welcomeBanner)
        welcomeBanner.text = "Welcome, ${user!!.NameFirst}!"

        // Search
        val searchField = findViewById<SearchView>(R.id.searchField)
        searchField.clearFocus()
        searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean
            {
                searchString = query
                loadListings()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean
            {
                searchString = newText
                loadListings()
                return true
            }

        })

        setupFilterDialog()
        setupSortDialog()
        loadListings()
    }

    fun fetchListings(): List<Listing>?
    {
        var listings = dbHelper.listings.getAll()

        // Search
        if(listings.isNotEmpty() && !searchString.isNullOrBlank())
        {
            listings = listings.filter {
                it.Name.contains(searchString!!, true)
                || it.Address.contains(searchString!!, true)
            }
        }

        // Filter
        if(listings.isNotEmpty() && filterPriceMin != null)
        {
            listings = listings.filter { it.PriceMin!! >= filterPriceMin!! }
        }

        if(listings.isNotEmpty() && filterPriceMax != null)
        {
            listings = listings.filter { it.PriceMax!! <= filterPriceMax!! }
        }

        if(listings.isNotEmpty() && filterAveRating != null)
        {
            listings = listings.filter { it.Rating!! >= filterAveRating!! }
        }

        if(listings.isEmpty())
        {
            return null
        }

        // Sort
        if(selectedSortCriteria == SortCriteria.NAME)
        {
            if(selectedSortDirection == SortDirections.ASCENDING)
            {
                listings = listings.sortedBy { it.Name }
            }
            else
            {
                listings = listings.sortedByDescending { it.Name }
            }
        }
        else if(selectedSortCriteria == SortCriteria.PRICE)
        {
            if(selectedSortDirection == SortDirections.ASCENDING)
            {
                listings = listings.sortedBy { it.PriceMin }
            }
            else
            {
                listings = listings.sortedByDescending { it.PriceMin }
            }
        }
        else
        {
            if(selectedSortDirection == SortDirections.ASCENDING)
            {
                listings = listings.sortedBy { it.Rating }
            }
            else
            {
                listings = listings.sortedByDescending { it.Rating }
            }
        }

        return listings
    }

    fun loadListings()
    {
        val listings = fetchListings()

        val noListingsText: TextView = findViewById(R.id.noListingsText)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        noListingsText.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE

        if (!listings.isNullOrEmpty())
        {
            noListingsText.visibility = View.INVISIBLE
            recyclerView.visibility = View.VISIBLE

            val adapter = ListingAdapter(listings, user.Id!!)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    fun logout(view: View)
    {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun createDialog(context: Context): Dialog
    {
        val newDialog = Dialog(context)
        newDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        newDialog.setCancelable(false)
        newDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return newDialog
    }

    fun setupFilterDialog()
    {
        dialogFilter = createDialog(this)
        dialogFilter.setContentView(R.layout.dialog_filter)

        val fieldPriceMin = dialogFilter.findViewById<ValEditText>(R.id.etMinPrice)
        val fieldPriceMax = dialogFilter.findViewById<ValEditText>(R.id.etMaxPrice)
        val fieldAveRating= dialogFilter.findViewById<ValEditText>(R.id.etAveRate)

        val reset =dialogFilter.findViewById<ImageButton>(R.id.resetBtn)
        val cancel = dialogFilter.findViewById<ImageButton>(R.id.cancelBtn)
        val apply = dialogFilter.findViewById<ImageButton>(R.id.applyBtn)

        reset.setOnClickListener(){
            fieldPriceMin.text = null
            fieldPriceMax.text = null
            fieldAveRating.text = null
        }
        cancel.setOnClickListener(){
            dialogFilter.hide()
        }
        apply.setOnClickListener(){
            // Parse input
            filterPriceMin = fieldPriceMin.text?.toDoubleOrNull()
            filterPriceMax = fieldPriceMax.text?.toDoubleOrNull()
            filterAveRating = fieldAveRating.text?.toDoubleOrNull()

            // Reset field validation states
            fieldPriceMin.error = null
            fieldPriceMax.error = null
            fieldAveRating.error = null

            // Validate min price
            if(filterPriceMin == null)
            {
                filterPriceMin = 0.0
            }
            else if(filterPriceMin!! < 0.0)
            {
                fieldPriceMin.error = "Minimum price cannot be a negative value"
            }

            // Validate max price
            if(filterPriceMax != null)
            {
                if(filterPriceMax!! < 0.0)
                {
                    fieldPriceMax.error = "Maximum price cannot be a negative value"
                }
                else if(filterPriceMin!! > filterPriceMax!!)
                {
                    fieldPriceMax.error = "Maximum price cannot be less than minimum price"
                }
            }

            // Validate ave rating
            if(filterAveRating != null)
            {
                if(filterAveRating!! < 0.0)
                {
                    fieldAveRating.error = "Average rating cannot be a negative value"
                }
                else if(filterAveRating!! > 5.0)
                {
                    fieldAveRating.error = "Average rating cannot be greater than 5"
                }
            }

            val fieldPriceMinIsValid = fieldPriceMin.error.isNullOrBlank()
            val fieldPriceMaxIsValid = fieldPriceMax.error.isNullOrBlank()
            val fieldAveRatingIsValid = fieldAveRating.error.isNullOrBlank()

            if(fieldPriceMinIsValid && fieldPriceMaxIsValid && fieldAveRatingIsValid)
            {
                loadListings()
                Toast.makeText(this, "Applied filtering", Toast.LENGTH_SHORT).show()
                dialogFilter.hide()
            }
        }
    }

    fun setupSortDialog()
    {
        dialogSort = createDialog(this)
        dialogSort.setContentView(R.layout.dialog_sort)

        val rb1 = dialogSort.findViewById<RadioButton>(R.id.rb1)
        val rb2 = dialogSort.findViewById<RadioButton>(R.id.rb2)
        val rb3 = dialogSort.findViewById<RadioButton>(R.id.rb3)
        val radioGroup = dialogSort.findViewById<RadioGroup>(R.id.radioGroup)

        val rbf1 = dialogSort.findViewById<RadioButton>(R.id.rbf1)
        val rbf2 = dialogSort.findViewById<RadioButton>(R.id.rbf2)
        val radioGroup2 = dialogSort.findViewById<RadioGroup>(R.id.radioGroup2)

        val reset =dialogSort.findViewById<ImageButton>(R.id.resetBtn)
        val cancel = dialogSort.findViewById<ImageButton>(R.id.cancelBtn)
        val apply = dialogSort.findViewById<ImageButton>(R.id.applyBtn)

        radioGroup.check(selectedSortCriteriaControl)
        radioGroup2.check(selectedSortDirectionControl)

        rb1.setOnClickListener {
            selectedSortCriteriaControl = R.id.rb1
            selectedSortCriteria = SortCriteria.NAME
        }
        rb2.setOnClickListener {
            selectedSortCriteriaControl = R.id.rb2
            selectedSortCriteria = SortCriteria.AVERAGE_RATING
        }
        rb3.setOnClickListener {
            selectedSortCriteriaControl = R.id.rb3
            selectedSortCriteria = SortCriteria.PRICE
        }

        rbf1.setOnClickListener {
            selectedSortDirectionControl = R.id.rbf1
            selectedSortDirection = SortDirections.ASCENDING
        }
        rbf2.setOnClickListener {
            selectedSortDirectionControl = R.id.rbf2
            selectedSortDirection = SortDirections.DESCENDING
        }

        fun resetSortDialog()
        {
            selectedSortCriteriaControl = R.id.rb2
            selectedSortCriteria = SortCriteria.AVERAGE_RATING
            selectedSortDirectionControl = R.id.rbf2
            selectedSortDirection = SortDirections.DESCENDING
            radioGroup.check(selectedSortCriteriaControl)
            radioGroup2.check(selectedSortDirectionControl)
        }

        reset.setOnClickListener(){
            resetSortDialog()
        }

        cancel.setOnClickListener(){
            resetSortDialog()
            dialogSort.hide()
        }

        apply.setOnClickListener() {
            Toast.makeText(this, "Applied sorting", Toast.LENGTH_SHORT).show()
            loadListings()
            dialogSort.hide()
        }
    }

    fun showFilterDialog(view: View) = dialogFilter.show()

    fun showSortDialog(view: View) = dialogSort.show()
}

