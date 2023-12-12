package com.itg3.grp1.mobdevproject

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itg3.grp1.mobdevproject.data.DatabaseHelper
import com.itg3.grp1.mobdevproject.data.models.Listing
import com.itg3.grp1.mobdevproject.data.models.User

class ListingActivity: AppCompatActivity()
{
    private var selectedSortCriteriaControl: Int = R.id.rb2
    private var selectedSortDirectionControl: Int = R.id.rbf1
    private var selectedSortCriteria : SortCriteria = SortCriteria.AVERAGE_RATING
    private var selectedSortDirection : SortDirections = SortDirections.DESCENDING
    private lateinit var user : User

    private val dbHelper = DatabaseHelper(this)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing)

        user = dbHelper.users.getOne(intent.extras!!.getInt("userId"))!!
        val welcomeBanner: TextView = findViewById(R.id.welcomeBanner)
        welcomeBanner.text = "Welcome, ${user!!.NameFirst}!"

        loadListings()
    }

    fun fetchListings(): List<Listing>
    {
        var listings = dbHelper.listings.getAll()
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
        if (!listings.isNullOrEmpty())
        {
            val noListingsText: TextView = findViewById(R.id.noListingsText)
            noListingsText.visibility = View.INVISIBLE

            val adapter = ListingAdapter(listings, user.Id!!)
            val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
            recyclerView.visibility = View.VISIBLE
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
        }
    }

    fun logout(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun showFilterDialog(view: View) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_filter)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val minprice = dialog.findViewById<EditText>(R.id.etMinPrice)
        val maxprice = dialog.findViewById<EditText>(R.id.etMaxPrice)
        val averate= dialog.findViewById<EditText>(R.id.etAveRate)

        val reset =dialog.findViewById<ImageButton>(R.id.resetBtn)
        val cancel = dialog.findViewById<ImageButton>(R.id.cancelBtn)
        val apply = dialog.findViewById<ImageButton>(R.id.applyBtn)


        reset.setOnClickListener(){
        minprice.text = null
        maxprice.text = null
        averate.text = null
        }
        cancel.setOnClickListener(){
            dialog.dismiss()
        }
        apply.setOnClickListener(){
            Toast.makeText(this, "Applied filtering", Toast.LENGTH_SHORT).show()
        }
        dialog.show()
    }

    fun showSortDialog(view: View) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_sort)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val rb1 = dialog.findViewById<RadioButton>(R.id.rb1)
        val rb2 = dialog.findViewById<RadioButton>(R.id.rb2)
        val rb3 = dialog.findViewById<RadioButton>(R.id.rb3)
        val radioGroup = dialog.findViewById<RadioGroup>(R.id.radioGroup)

        val rbf1 = dialog.findViewById<RadioButton>(R.id.rbf1)
        val rbf2 = dialog.findViewById<RadioButton>(R.id.rbf2)
        val radioGroup2 = dialog.findViewById<RadioGroup>(R.id.radioGroup2)

        val reset =dialog.findViewById<ImageButton>(R.id.resetBtn)
        val cancel = dialog.findViewById<ImageButton>(R.id.cancelBtn)
        val apply = dialog.findViewById<ImageButton>(R.id.applyBtn)

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
            selectedSortDirectionControl = R.id.rbf1
            radioGroup.check(selectedSortCriteriaControl)
            radioGroup2.check(selectedSortDirectionControl)
        }

        reset.setOnClickListener(){
          resetSortDialog()
        }

        cancel.setOnClickListener(){
            resetSortDialog()
            dialog.hide()
        }

        apply.setOnClickListener() {
            Toast.makeText(this, "Applied sorting", Toast.LENGTH_SHORT).show()
            loadListings()
            dialog.hide()
        }
        dialog.show()
    }
}

