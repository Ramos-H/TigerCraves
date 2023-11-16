package com.itg3.grp1.mobdevproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var fieldEmail : EditText
    lateinit var fieldPass : EditText

    lateinit var btnLogin : Button
    lateinit var btnRegister : Button

    lateinit var validationTextEmail : TextView
    lateinit var validationTextPass : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
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

        if (email.isEmpty()) {
            setFieldValidationState(fieldEmail, validationTextEmail, View.VISIBLE,"Email is required")
        } else if (!isValidEmail(email)) {
            setFieldValidationState(fieldEmail, validationTextEmail, View.VISIBLE,"Invalid email format")
        } else {
            setFieldValidationState(fieldEmail, validationTextEmail, View.INVISIBLE)
        }

        if (pass.isEmpty()) {
            setFieldValidationState(fieldPass, validationTextPass, View.VISIBLE,"Password is required")
        } else if (!isValidPassword(pass)) {
            setFieldValidationState(fieldPass, validationTextPass, View.VISIBLE,"Password must be at least 8 characters")
        } else {
            setFieldValidationState(fieldPass, validationTextPass, View.INVISIBLE)
        }

        if (isValidEmail(email) && isValidPassword(pass)) {
            Toast.makeText(this, "LOGIN SUCCESSFUL", Toast.LENGTH_LONG).show()
        }
    }

    fun register(view: View)
    {
        Toast.makeText(this, "Navigated to Register", Toast.LENGTH_LONG).show()
    }
}