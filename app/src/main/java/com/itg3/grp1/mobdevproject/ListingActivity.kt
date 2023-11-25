package com.itg3.grp1.mobdevproject

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
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

class ListingActivity: AppCompatActivity() {
    private var selectedSortingOption: Int = R.id.rb1
    private var selectedSortingOption2: Int = R.id.rbf1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing)

        val dbHelper = DatabaseHelper(this)

        val user = dbHelper.users.getOne(intent.extras!!.getInt("userId"))
        val welcomeBanner: TextView = findViewById(R.id.welcomeBanner)
        welcomeBanner.text = "Welcome, ${user!!.NameFirst}!"

        val listings = dbHelper.listings.getAll()
        if (!listings.isNullOrEmpty()) {
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
        dialog.setContentView(R.layout.cardview_filter)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvTitle = dialog.findViewById<TextView>(R.id.tvTitle)
        val minprice = dialog.findViewById<EditText>(R.id.etMinPrice)
        val maxprice = dialog.findViewById<EditText>(R.id.etMaxPrice)
        val averate= dialog.findViewById<EditText>(R.id.etAveRate)

        val reset =dialog.findViewById<Button>(R.id.resetBtn)
        val cancel = dialog.findViewById<Button>(R.id.cancelBtn)
        val apply = dialog.findViewById<Button>(R.id.applyBtn)


        tvTitle.text = "Filtering"
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
        dialog.setContentView(R.layout.cardview_sort)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvTitle = dialog.findViewById<TextView>(R.id.tvTitle)
        val tvTitle2 = dialog.findViewById<TextView>(R.id.tvTitle2)
        val rb1 = dialog.findViewById<RadioButton>(R.id.rb1)
        val rb2 = dialog.findViewById<RadioButton>(R.id.rb2)
        val rb3 = dialog.findViewById<RadioButton>(R.id.rb3)
        val radioGroup = dialog.findViewById<RadioGroup>(R.id.radioGroup)
        val rbf1 = dialog.findViewById<RadioButton>(R.id.rbf1)
        val rbf2 = dialog.findViewById<RadioButton>(R.id.rbf2)
        val radioGroup2 = dialog.findViewById<RadioGroup>(R.id.radioGroup2)
        val reset =dialog.findViewById<Button>(R.id.resetBtn)
        val cancel = dialog.findViewById<Button>(R.id.cancelBtn)
        val apply = dialog.findViewById<Button>(R.id.applyBtn)

        radioGroup.check(selectedSortingOption)
        radioGroup2.check(selectedSortingOption2)

        tvTitle.text = "Sorting Criteria"
        tvTitle2.text = "Sorting Direction"

        rb1.setOnClickListener {
            selectedSortingOption = R.id.rb1
        }

        rb2.setOnClickListener {
            selectedSortingOption = R.id.rb2
        }
        rb3.setOnClickListener {
            selectedSortingOption = R.id.rb3
        }

        rbf1.setOnClickListener {

            selectedSortingOption2 = R.id.rbf1
        }
        rbf2.setOnClickListener {
            selectedSortingOption2 = R.id.rbf2
        }
        reset.setOnClickListener(){
            selectedSortingOption = R.id.rb1
            selectedSortingOption2 = R.id.rbf1
            radioGroup.check(selectedSortingOption)
            radioGroup2.check(selectedSortingOption2)
        }
        cancel.setOnClickListener(){
            dialog.dismiss()
            selectedSortingOption = R.id.rb1
            selectedSortingOption2 = R.id.rbf1
            radioGroup.check(selectedSortingOption)
            radioGroup2.check(selectedSortingOption2)
        }
        apply.setOnClickListener(){
            Toast.makeText(this, "Applied sorting", Toast.LENGTH_SHORT).show()
        }
        dialog.show()
    }
}

