package com.itg3.grp1.mobdevproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.itg3.grp1.mobdevproject.models.Listing
import com.itg3.grp1.mobdevproject.models.Review
import com.itg3.grp1.mobdevproject.models.User

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TestDb()
    }

    private fun TestDb()
    {
        val db = DatabaseHandler(this)

        val user1 = User(null, "Hans", "Rebeta", "Ramos", "hans@mail.com", "mypass", null)
        val user2 = User(null, "Hans Simon", "Rebeta2", "Ramosy", "hans2@mail.com", "mypass2", null)
        db.users.add(user1)
        db.users.add(user2)
        user1.Id = 1
        user2.Id = 2

        val listing1 = Listing(null, user1, "Bon Appetea", "Valley 1", 75.0, 200.0, 3.0, null)
        val listing2 = Listing(null, user2, "Bon-chon", "UST", 100.0, 800.0, null, null)
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
        for (user in db.users.getAll()) Log.d("SQL Get users", user.toString())
        for (listing in db.listings.getAll()) Log.d("SQL Get listing", listing.toString())
        for (review in db.reviews.getAll()) Log.d("SQL Get review", review.toString())

        db.users.update(User(1, "Spider", "-", "Man", "spidey@mail.com", "spideypass", null))
        db.listings.update(Listing(1, user1, "Bon Appetea", "Valley 1", 75.0, 200.0, 5.0, null))
        db.reviews.update(Review(1, user1, listing1, 3.0, "Not the Best", "Boompanes", null))
        for (user in db.users.getAll()) Log.d("SQL Get users 2", user.toString())
        for (listing in db.listings.getAll()) Log.d("SQL Get listing 2", listing.toString())
        for (review in db.reviews.getAll()) Log.d("SQL Get review 2", review.toString())

        db.users.delete(User(1, "", "", "", "", "", null))
        db.listings.delete(Listing(1, user1, "", "", null, null, null, null))
        db.reviews.delete(Review(1, user1, listing1, 0.0, "", ""))
        for (user in db.users.getAll()) Log.d("SQL Get users 3", user.toString())
        for (listing in db.listings.getAll()) Log.d("SQL Get listing 3", listing.toString())
        for (review in db.reviews.getAll()) Log.d("SQL Get review 3", review.toString())
    }
}