package com.itg3.grp1.mobdevproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.itg3.grp1.mobdevproject.data.DatabaseHelper
import com.itg3.grp1.mobdevproject.data.models.User

class RegisterActivity : AppCompatActivity()
{
    lateinit var fieldEmail: ValEditText
    lateinit var fieldNameFirst: ValEditText
    lateinit var fieldNameMiddle: ValEditText
    lateinit var fieldNameLast: ValEditText
    lateinit var fieldPassword: ValEditText
    lateinit var fieldPasswordConfirm: ValEditText

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        fieldEmail = findViewById(R.id.fieldEmail)
        fieldNameFirst = findViewById(R.id.fieldNameFirst)
        fieldNameMiddle = findViewById(R.id.fieldNameMiddle)
        fieldNameLast = findViewById(R.id.fieldNameLast)
        fieldPassword = findViewById(R.id.fieldPassword)
        fieldPasswordConfirm = findViewById(R.id.fieldPasswordConfirm)
    }

    fun register(view: View)
    {
        // Reset validation states
        fieldEmail.error = null
        fieldNameFirst.error = null
        fieldNameMiddle.error = null
        fieldNameLast.error = null
        fieldPassword.error = null
        fieldPasswordConfirm.error = null

        val nameFirst = fieldNameFirst.text?.trim()
        val nameMiddle = fieldNameMiddle.text?.trim()
        val nameLast = fieldNameLast.text?.trim()
        val email = fieldEmail.text?.trim()

        if(nameFirst.isNullOrBlank())
        {
            fieldNameFirst.error = "First Name cannot be empty"
        }
        else if(nameFirst.length > 255)
        {
            fieldNameFirst.error = "First Name must be 255 characters or less"
        }

        if(nameMiddle!!.length > 255)
        {
            fieldNameMiddle.error = "Middle Name must be 255 characters or less"
        }

        if(nameLast.isNullOrBlank())
        {
            fieldNameLast.error = "Last Name cannot be empty"
        }
        else if(nameLast.length > 255)
        {
            fieldNameLast.error = "Last Name must be 255 characters or less"
        }

        if (email.isNullOrBlank())
        {
            // Email is empty
            fieldEmail.error = "Email cannot be empty"
        }
        else if (email.length > 255)
        {
            // Email exceeds maximum length
            fieldEmail.error = "Email must be 255 characters or less"
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            // Invalid email
            fieldEmail.error = "Invalid email format"
        }

        if (fieldPassword.text.isNullOrBlank())
        {
            // Password is empty
            fieldPassword.error =  "Password cannot be empty"
        }
        else if(fieldPassword.text.toString().length > 2048)
        {
            fieldPassword.error = "Password must be 2048 characters or less"
        }
        else if (!fieldPassword.text!!.matches(Regex("(?=.*[0-9]).{8,}")))
        {
            // Invalid password
            fieldPassword.error =  "Must be at least 8 alphanumeric characters"
        }

        if (fieldPasswordConfirm.text.isNullOrBlank())
        {
            // Confirm Password is empty
            fieldPasswordConfirm.error = "Confirm password cannot be empty"
        }
        else if (fieldPasswordConfirm.text != fieldPassword.text)
        {
            // Passwords don't match
            fieldPasswordConfirm.error = "Passwords do not match"
        }

        val dbHelper = DatabaseHelper(this)
        val usersWithSameEmail = dbHelper.users.getAll().filter { email == it.Email }

        if(usersWithSameEmail.isNotEmpty())
        {
            fieldEmail.error = "Email has already been registered"
        }

        // Check if any validation failed
        val formHasErrors = !fieldNameFirst.error.isNullOrBlank() ||
                !fieldNameMiddle.error.isNullOrBlank() ||
                !fieldNameLast.error.isNullOrBlank() ||
                !fieldEmail.error.isNullOrBlank() ||
                !fieldPassword.error.isNullOrBlank() ||
                !fieldPasswordConfirm.error.isNullOrBlank()

        if (formHasErrors)
        {
            return
        }

        val newUser = User(
            null,
            nameFirst!!,
            nameMiddle,
            nameLast!!,
            email!!,
            fieldPassword.text!!
        )

        val newUserId = dbHelper.users.add(newUser)

        if(newUserId.toInt() == -1)
        {
            Toast.makeText(this, "Registration unsuccessful. Probably a database error.", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, "Registration successful. Please log in.", Toast.LENGTH_SHORT).show()

        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun goToLogin(view: View) = startActivity(Intent(this, LoginActivity::class.java))
}
