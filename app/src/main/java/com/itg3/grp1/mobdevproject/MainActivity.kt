package com.itg3.grp1.mobdevproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)
        var emailEditText = findViewById<EditText>(R.id.etEmail)
        var passEditText = findViewById<EditText>(R.id.etPassword)
        var loginBtn = findViewById<Button>(R.id.loginBtn)
        var regBtn = findViewById<Button>(R.id.regBtn)
        val validationTextEmail = findViewById<TextView>(R.id.validationTextView)
        val validationTextPass = findViewById<TextView>(R.id.validationTextView2)
        val text = "Navigated to Register"
        val validTxt = "LOGIN SUCCESSFUL"
        validationTextEmail.visibility = View.INVISIBLE
        validationTextPass.visibility = View.INVISIBLE

        fun isValidEmail(email: String): Boolean {
            return email.length <= 255 && android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        }

        fun setFieldValidationState(field: EditText, validationDisplay: TextView, visibility: Int, message: String? = null) {
            field.error = message
            validationDisplay.visibility = visibility
            validationDisplay.text = message
        }

        fun isValidPassword(password: String): Boolean {
            // Condition 1: Password must be at least 8 characters
            return password.length >= 8 && password.matches(Regex("^[a-zA-Z0-9]+$"))
        }
        loginBtn.setOnClickListener() {
            val email = emailEditText.text.toString()
            val pass = passEditText.text.toString()

            if (email.isEmpty()) {
                setFieldValidationState(emailEditText, validationTextEmail, View.VISIBLE,"Email is required")
            } else if (!isValidEmail(email)) {
                setFieldValidationState(emailEditText, validationTextEmail, View.VISIBLE,"Invalid email format")
            } else {
                setFieldValidationState(emailEditText, validationTextEmail, View.INVISIBLE)
            }

            if (pass.isEmpty()) {
                setFieldValidationState(passEditText, validationTextPass, View.VISIBLE,"Password is required")
            } else if (!isValidPassword(pass)) {
                setFieldValidationState(passEditText, validationTextPass, View.VISIBLE,"Password must be at least 8 characters")
            } else {
                setFieldValidationState(passEditText, validationTextPass, View.INVISIBLE)
            }

            if (isValidEmail(email) && isValidPassword(pass)) {
                Toast.makeText(this, "LOGIN SUCCESSFUL", Toast.LENGTH_LONG).show()
            }
        }
        regBtn.setOnClickListener() {
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }
    }
}