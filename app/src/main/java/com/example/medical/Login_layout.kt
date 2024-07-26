package com.example.medical

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.medical.R.id.btnLogIn

import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main

import kotlinx.coroutines.launch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login_layout : AppCompatActivity() {
    //private lateinit var mUserViewModel: UserViewModel
    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var btnSignin: TextView
    private lateinit var btnNewUser: Button
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnNewUser=findViewById(R.id.btnNewUser)
        btnNewUser.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
       //mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        btnSignin = findViewById(btnLogIn)
        emailLayout = findViewById(R.id.email)
        passwordLayout = findViewById(R.id.password)
        auth = FirebaseAuth.getInstance()
        val email = emailLayout.editText?.getText().toString()
        val password = passwordLayout.editText?.getText().toString()
        btnSignin.setOnClickListener {
            if (true) {
                val email = emailLayout.editText?.text.toString() ?: return@setOnClickListener
                val password = passwordLayout.editText?.text.toString() ?: return@setOnClickListener

                // Launch coroutine using viewModelScope
                lifecycleScope.launch(Dispatchers.IO) {
                    performLogin(email ,password)
                }
            } else {
                // Show error messages if validation fails (already implemented in your code)
            }
        }
    }
    private fun performLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login successful
                    val userId = auth.currentUser?.uid // Get the user ID
                    saveUserIdToSharedPref(userId!!) // Save user ID to SharedPreferences
                    navigateToMainActivity() // Navigate to main activity
                } else {
                    // Login failed
                    Log.w("LoginActivity", "signInWithEmailAndPassword failed.", task.exception)
                    Toast.makeText(this, "Login failed. Please check your email and password.", Toast.LENGTH_LONG).show()
                }
            }
    }
    private fun saveUserIdToSharedPref(userId: String) {
        val sharedPref = getSharedPreferences("login_pref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("user_id", userId)
        editor.putBoolean("isLoggedIn", true)


        System.out.println("user_id user_id: $userId")
        editor.apply()
    }

    private fun navigateToMainActivity() {
        // Replace with your actual main activity class name
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Close login activity
    }

}