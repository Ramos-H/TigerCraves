package com.itg3.grp1.mobdevproject

import android.content.Intent
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

        fun resetFieldState(editText: EditText, validationText: TextView) {
            editText.setError(null)
            validationText.visibility = View.INVISIBLE
        }

        fun isValidPassword(password: String): Boolean {
            // Condition 1: Password must be at least 8 characters
            return password.length >= 8 && password.matches(Regex("^[a-zA-Z0-9]+$"))
        }
        loginBtn.setOnClickListener() {
            val email = emailEditText.text.toString()
            val pass = passEditText.text.toString()
            if (email.isEmpty()) {
                emailEditText.setError("Email is required")
                validationTextEmail.visibility = View.VISIBLE
                validationTextEmail.text = "Email is required"
                validationTextEmail.setTextColor(resources.getColor(android.R.color.holo_red_light))
            } else if (!isValidEmail(email)) {
                emailEditText.setError("Invalid email")
                validationTextEmail.visibility = View.VISIBLE
                validationTextEmail.text = "Invalid email format"
                validationTextEmail.setTextColor(resources.getColor(android.R.color.holo_red_light))
            } else {
                resetFieldState(emailEditText, validationTextEmail)
            }
            if (pass.isEmpty()) {
                passEditText.setError("Password is required")
                validationTextPass.visibility = View.VISIBLE
                validationTextPass.text = "Password is required"
                validationTextPass.setTextColor(resources.getColor(android.R.color.holo_red_light))
            } else if (!isValidPassword(pass)) {
                validationTextPass.visibility = View.VISIBLE
                validationTextPass.text = "Invalid pass format"
                validationTextPass.text = "Password must be at least 8 characters."
                validationTextPass.setTextColor(resources.getColor(android.R.color.holo_red_light))
            } else {
                resetFieldState(passEditText, validationTextPass)
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