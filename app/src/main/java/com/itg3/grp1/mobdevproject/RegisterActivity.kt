package com.itg3.grp1.mobdevproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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

        if(fieldNameFirst.text.isNullOrBlank())
        {
            fieldNameFirst.error = "First Name cannot be empty"
        }

        if(fieldNameLast.text.isNullOrBlank())
        {
            fieldNameLast.error = "Last Name cannot be empty"
        }

        if (fieldEmail.text.isNullOrBlank())
        {
            // Email is empty
            fieldEmail.error = "Email cannot be empty."
        }
        else if (fieldEmail.text!!.length > 255)
        {
            // Email exceeds maximum length
            fieldEmail.error = "Email must be 255 characters or less."
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(fieldEmail.text).matches())
        {
            // Invalid email
            fieldEmail.error = "Invalid email format."
        }

        if (fieldPassword.text.isNullOrBlank())
        {
            // Password is empty
            fieldPassword.error =  "Password cannot be empty."
        }
        else if (!fieldPassword.text!!.matches(Regex("(?=.*[0-9]).{8,}")))
        {
            // Invalid password
            fieldPassword.error =  "Must be at least 8 alphanumeric characters."
        }

        if (fieldPasswordConfirm.text.isNullOrBlank())
        {
            // Confirm Password is empty
            fieldPasswordConfirm.error = "Confirm password cannot be empty."
        }
        else if (fieldPasswordConfirm.text != fieldPassword.text)
        {
            // Passwords don't match
            fieldPasswordConfirm.error = "Passwords do not match."
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

        Toast.makeText(this, "Registration successful. Please log in.", Toast.LENGTH_SHORT).show()

        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun goToLogin(view: View) = startActivity(Intent(this, LoginActivity::class.java))
}
