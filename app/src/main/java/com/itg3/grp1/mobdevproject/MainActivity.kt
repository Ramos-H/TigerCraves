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
        var pass = findViewById<EditText>(R.id.etPassword)
        var loginBtn= findViewById<Button>(R.id.loginBtn)
        var regBtn = findViewById<Button>(R.id.regBtn)
        val validationTextEmail = findViewById<TextView>(R.id.validationTextView)
        val text = "Navigated to Register"
        validationTextEmail.visibility = View.INVISIBLE

        loginBtn.setOnClickListener() {
            val email = emailEditText.text.toString()
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

            }
            else {
                resetFieldState(emailEditText, validationTextEmail)
            }
        }
        regBtn.setOnClickListener(){
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }



    }
    private fun isValidEmail(email: String): Boolean {
        return email.length <= 255 && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun resetFieldState(editText: EditText, validationText: TextView) {
        editText.setError(null)
        validationText.visibility = View.INVISIBLE
    }
}