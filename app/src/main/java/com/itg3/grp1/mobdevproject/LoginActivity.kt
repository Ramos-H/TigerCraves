package com.itg3.grp1.mobdevproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.itg3.grp1.mobdevproject.models.Listing
import com.itg3.grp1.mobdevproject.models.Review
import com.itg3.grp1.mobdevproject.models.User
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    lateinit var fieldEmail : EditText
    lateinit var fieldPass : EditText

    lateinit var btnLogin : Button
    lateinit var btnRegister : Button

    lateinit var validationTextEmail : TextView
    lateinit var validationTextPass : TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        fieldEmail = findViewById<EditText>(R.id.etEmail)
        fieldPass = findViewById<EditText>(R.id.etPassword)
        btnLogin = findViewById<Button>(R.id.loginBtn)
        btnRegister = findViewById<Button>(R.id.regBtn)
        validationTextEmail = findViewById<TextView>(R.id.validationTextView)
        validationTextPass = findViewById<TextView>(R.id.validationTextView2)

        validationTextEmail.visibility = View.INVISIBLE
        validationTextPass.visibility = View.INVISIBLE

        TestDb()
    }

    private fun isValidEmail(email: String): Boolean
    {
        return email.length <= 255 && android.util.Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()
    }

    private fun isValidPassword(password: String): Boolean
    {
        // Condition 1: Password must be at least 8 characters
        return password.length >= 8 && password.matches(Regex("^[a-zA-Z0-9]+$"))
    }

    private fun setFieldValidationState(field: EditText, validationDisplay: TextView,
                                        visibility: Int, message: String? = null)
    {
        field.error = message
        validationDisplay.visibility = visibility
        validationDisplay.text = message
    }

    fun login(view: View)
    {
        val email = fieldEmail.text.toString()
        val pass = fieldPass.text.toString()

        if (email.isNullOrBlank()) {
            setFieldValidationState(fieldEmail, validationTextEmail, View.VISIBLE,"Email is required")
        } else if (!isValidEmail(email)) {
            setFieldValidationState(fieldEmail, validationTextEmail, View.VISIBLE,"Invalid email format")
        } else {
            setFieldValidationState(fieldEmail, validationTextEmail, View.INVISIBLE)
        }

        if (pass.isNullOrBlank()) {
            setFieldValidationState(fieldPass, validationTextPass, View.VISIBLE,"Password is required")
        } else if (!isValidPassword(pass)) {
            setFieldValidationState(fieldPass, validationTextPass, View.VISIBLE,"Password must be at least 8 characters")
        } else {
            setFieldValidationState(fieldPass, validationTextPass, View.INVISIBLE)
        }

        if (isValidEmail(email) && isValidPassword(pass)) {
            val intent = Intent(this, ListingActivity::class.java)
            startActivity(intent)
        }
    }

    fun register(view: View)
    {
        Toast.makeText(this, "Navigated to Register", Toast.LENGTH_LONG).show()
    }

    private fun TestDb()
    {
        val db = DatabaseHelper(this)

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
        Log.d("SQL Get listing", db.listings.getOne(1).toString())
        Log.d("SQL Get review", db.reviews.getOne(1).toString())

        for (user in db.users.getAll()) Log.d("SQL Get users", user.toString())
        for (listing in db.listings.getAll()) Log.d("SQL Get listings", listing.toString())
        for (review in db.reviews.getAll()) Log.d("SQL Get reviews", review.toString())

        db.users.update(User(1, "Spider", "-", "Man", "spidey@mail.com", "spideypass", null))
        db.listings.update(Listing(1, user1, "Bon Appetea", "Valley 1", 75.0, 200.0, 5.0, null))
        db.reviews.update(Review(1, user1, listing1, 3.0, "Not the Best", "Boompanes", null))
        for (user in db.users.getAll()) Log.d("SQL Get users 2", user.toString())
        for (listing in db.listings.getAll()) Log.d("SQL Get listings 2", listing.toString())
        for (review in db.reviews.getAll()) Log.d("SQL Get reviews 2", review.toString())

        db.users.delete(User(1, "", "", "", "", "", null))
        db.listings.delete(Listing(1, user1, "", "", null, null, null, null))
        db.reviews.delete(Review(1, user1, listing1, 0.0, "", ""))
        for (user in db.users.getAll()) Log.d("SQL Get users 3", user.toString())
        for (listing in db.listings.getAll()) Log.d("SQL Get listings 3", listing.toString())
        for (review in db.reviews.getAll()) Log.d("SQL Get reviews 3", review.toString())
    }
}