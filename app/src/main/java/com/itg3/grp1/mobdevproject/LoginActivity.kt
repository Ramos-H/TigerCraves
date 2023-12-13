package com.itg3.grp1.mobdevproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itg3.grp1.mobdevproject.data.models.User
import android.view.View
import com.itg3.grp1.mobdevproject.data.DatabaseHelper
import com.itg3.grp1.mobdevproject.data.DatabaseSeeder

class LoginActivity : AppCompatActivity() {
    lateinit var fieldEmail : ValEditText
    lateinit var fieldPassword : ValEditText

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val dbHelper = DatabaseHelper(this)
        val dbSeeder = DatabaseSeeder(dbHelper)
//        dbSeeder.seedUsers()
//        dbSeeder.seedListings()
//        dbSeeder.seedReviews()

        fieldEmail = findViewById(R.id.fieldEmail)
        fieldPassword = findViewById(R.id.fieldPassword)
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
}