package com.itg3.grp1.mobdevproject

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var textViewEmailValidation: TextView
    private lateinit var textViewPasswordValidation: TextView
    private lateinit var textViewConfirmPasswordValidation: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)

        textViewEmailValidation = findViewById(R.id.textViewEmailValidation)
        textViewPasswordValidation = findViewById(R.id.textViewPasswordValidation)
        textViewConfirmPasswordValidation = findViewById(R.id.textViewConfirmPasswordValidation)

        val buttonRegister: ImageButton = findViewById(R.id.buttonRegister)
        val buttonLogin: ImageButton = findViewById(R.id.buttonLogin)

        buttonRegister.setOnClickListener {
            validateAndRegister()
        }

        buttonLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun validateAndRegister() {
        // Reset validation states
        resetValidationState(editTextEmail, textViewEmailValidation)
        resetValidationState(editTextPassword, textViewPasswordValidation)
        resetValidationState(editTextConfirmPassword, textViewConfirmPasswordValidation)

        // Perform validation
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()
        val confirmPassword = editTextConfirmPassword.text.toString()

        if (TextUtils.isEmpty(email)) {
            // Email is empty
            setValidationState(editTextEmail, textViewEmailValidation, "  Field cannot be empty.")
        } else if (email.length > 255) {
            // Email exceeds maximum length
            setValidationState(editTextEmail, textViewEmailValidation, "        Email must be 255 characters or less.")
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Invalid email
            setValidationState(editTextEmail, textViewEmailValidation, "  Invalid email.")
        }

        if (TextUtils.isEmpty(password)) {
            // Password is empty
            setValidationState(
                editTextPassword,
                textViewPasswordValidation,
                "  Field cannot be empty."
            )
        } else if (!password.matches(Regex("(?=.*[0-9]).{8,}"))) {
            // Invalid password
            setValidationState(
                editTextPassword,
                textViewPasswordValidation,
                "          Must be at least 8 alphanumeric characters."
            )
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            // Confirm Password is empty
            setValidationState(
                editTextConfirmPassword,
                textViewConfirmPasswordValidation,
                "  Field cannot be empty."
            )
        } else if (confirmPassword != password) {
            // Passwords don't match
            setValidationState(
                editTextConfirmPassword,
                textViewConfirmPasswordValidation,
                "  Passwords do not match."
            )
        }

        // Check if any validation failed
        if (!TextUtils.isEmpty(email) && email.length <= 255 &&
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
            !TextUtils.isEmpty(password) && password.matches(Regex("(?=.*[0-9]).{8,}")) &&
            !TextUtils.isEmpty(confirmPassword) && confirmPassword == password
        ) {
            // Registration successful
            Toast.makeText(this, "REGISTER SUCCESSFUL", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToLogin() {
        Toast.makeText(this, "Navigated to Login", Toast.LENGTH_SHORT).show()
    }

    private fun setValidationState(
        editText: EditText,
        validationTextView: TextView,
        validationMessage: String
    ) {
        editText.setTextColor(resources.getColor(R.color.colorError))
        editText.setBackgroundResource(R.drawable.edittext_border_error)
        validationTextView.text = validationMessage
        validationTextView.setTextColor(resources.getColor(R.color.colorError))
        validationTextView.visibility = View.VISIBLE
    }

    private fun resetValidationState(editText: EditText, validationTextView: TextView) {
        editText.setTextColor(resources.getColor(R.color.colorDefaultText))
        editText.setBackgroundResource(R.drawable.edittext_border_default)
        validationTextView.text = ""
        validationTextView.visibility = View.INVISIBLE
    }
}
