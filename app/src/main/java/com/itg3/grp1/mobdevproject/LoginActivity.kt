package com.itg3.grp1.mobdevproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.itg3.grp1.mobdevproject.data.models.Listing
import com.itg3.grp1.mobdevproject.data.models.Review
import com.itg3.grp1.mobdevproject.data.models.User
import android.view.View
import android.widget.Button
import com.itg3.grp1.mobdevproject.data.DatabaseHelper

class LoginActivity : AppCompatActivity() {
    lateinit var fieldEmail : ValEditText
    lateinit var fieldPassword : ValEditText

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        AddTestListing()
    }

    private fun AddTestListing()
    {
        val dbHelper = DatabaseHelper(this)
        val count = dbHelper.listings.getAll().count() + 1
        dbHelper.listings.add(Listing(null, "Bon-Chon $count", "UST", 50.0, 300.0, 0.0))
    }

    fun login(view: View)
    {
        fieldEmail.error = null
        fieldPassword.error = null

        val email = fieldEmail.text?.trim()

        if(email.isNullOrBlank())
        {
            fieldEmail.error = "Email is required"
        }

        if(fieldPassword.text.isNullOrBlank())
        {
            fieldPassword.error = "Password is required"
        }

        val dbHelper = DatabaseHelper(this)
        val users = dbHelper.users.getAll()
        val usersWithSameEmails = users.filter { email == it.Email }
        var matchingUser : User? = null
        if(usersWithSameEmails.isEmpty())
        {
            fieldEmail.error = "No accounts are registered with that email"
        }
        else
        {
            matchingUser = usersWithSameEmails.filter { fieldPassword.text == it.PasswordHash }.firstOrNull()
            if(matchingUser == null)
            {
                fieldPassword.error = "Incorrect password"
            }
        }

        if (!fieldEmail.error.isNullOrBlank() || !fieldPassword.error.isNullOrBlank())
        {
            return
        }

        val intent = Intent(this, ListingActivity::class.java)
        intent.putExtra("userId", matchingUser!!.Id)
        startActivity(intent)
    }

    fun goToRegister(view: View) = startActivity(Intent(this, RegisterActivity::class.java))

    private fun TestDb()
    {
        val db = DatabaseHelper(this)

        val user1 = User(null, "Hans", "Rebeta", "Ramos", "hans@mail.com", "mypass", null)
        val user2 = User(null, "Hans Simon", "Rebeta2", "Ramosy", "hans2@mail.com", "mypass2", null)
        db.users.add(user1)
        db.users.add(user2)
        user1.Id = 1
        user2.Id = 2

        val listing1 = Listing(null, "Bon Appetea", "Valley 1", 75.0, 200.0, 3.0, null)
        val listing2 = Listing(null, "Bon-chon", "UST", 100.0, 800.0, null, null)
        db.listings.add(listing1)
        db.listings.add(listing2)
        listing1.Id = 1
        listing2.Id = 2

        val review1 = Review(null, user1, listing1, 5.0, "Best", "Boom", null)
        val review2 = Review(null, user2, listing2, 3.0, "Ok", "Bonk", null)
        db.reviews.add(review1)
        db.reviews.add(review2)
        review1.Id = 1
        review2.Id = 2

        Log.d("SQL Get user", db.users.getOne(1).toString())
        Log.d("SQL Get listing", db.listings.getOne(1).toString())
        Log.d("SQL Get review", db.reviews.getOne(1).toString())

        for (user in db.users.getAll()) Log.d("SQL Get users", user.toString())
        for (listing in db.listings.getAll()) Log.d("SQL Get listings", listing.toString())
        for (review in db.reviews.getAll()) Log.d("SQL Get reviews", review.toString())

        db.users.update(User(1, "Spider", "-", "Man", "spidey@mail.com", "spideypass", null))
        db.listings.update(Listing(1, "Bon Appetea", "Valley 1", 75.0, 200.0, 5.0, null))
        db.reviews.update(Review(1, user1, listing1, 3.0, "Not the Best", "Boompanes", null))
        for (user in db.users.getAll()) Log.d("SQL Get users 2", user.toString())
        for (listing in db.listings.getAll()) Log.d("SQL Get listings 2", listing.toString())
        for (review in db.reviews.getAll()) Log.d("SQL Get reviews 2", review.toString())

        db.users.delete(User(1, "", "", "", "", "", null))
        db.listings.delete(Listing(1, "", "", null, null, null, null))
        db.reviews.delete(Review(1, user1, listing1, 0.0, "", ""))
        for (user in db.users.getAll()) Log.d("SQL Get users 3", user.toString())
        for (listing in db.listings.getAll()) Log.d("SQL Get listings 3", listing.toString())
        for (review in db.reviews.getAll()) Log.d("SQL Get reviews 3", review.toString())
    }
}