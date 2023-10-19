package com.example.radarbird

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // Make sure this is your login layout

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val btnLogin = findViewById<Button>(R.id.button)  // your button's ID
        val txtEmail = findViewById<EditText>(R.id.editTextTextPersonName)  // your email EditText's ID
        val txtPassword = findViewById<EditText>(R.id.editTextTextPassword3)  // your password EditText's ID

        btnLogin.setOnClickListener {
            val email = txtEmail.text.toString().trim()
            val password = txtPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    val user: FirebaseUser? = auth.currentUser
                    Toast.makeText(this, "Successfully logged in: ${user?.email}", Toast.LENGTH_SHORT).show()

                    // Navigate to Dashboard Activity or any other activity
                    val intent = Intent(this, HomeActivity::class.java) // Replace DashboardActivity with your next activity class
                    startActivity(intent)
                    finish()
                } else {
                    // Sign in fails
                    Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}