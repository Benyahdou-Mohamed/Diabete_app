package com.example.medical

import android.R.attr.password
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SignUp : AppCompatActivity() {


    private lateinit var usernameLayout: TextInputLayout

    private lateinit var emailLayout: TextInputLayout

    private lateinit var numeroLayout: TextInputLayout

    private lateinit var passwordLayout: TextInputLayout
    private lateinit var btnSignUp: TextView
    private lateinit var btnLogIn: Button
    private lateinit var auth: FirebaseAuth

    fun validateUsername(): Boolean {
        val username = usernameLayout.editText?.text.toString()  // Safe null check for EditText
        val noWhiteSpace = "\\A\\w{4,20}\\z"
        if (username.isEmpty()) {
            usernameLayout.error = "Field cannot be empty"  // Use property setter for error message
            return false
        } else if (username.length > 15) {
            usernameLayout.error = "Username too long"
            return false
        }else if (username.contains("\\s".toRegex())) { // Check for whitespace characters
            usernameLayout.error = "White spaces are not allowed"
            return false
        }
        return true // Username is valid
    }
    fun validateEmail(): Boolean {
        val email = emailLayout.editText?.text.toString()  // Safe null check for EditText

        if (email.isEmpty()) {
            emailLayout.error = "Field cannot be empty"
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Use built-in email pattern matcher
            emailLayout.error = "Invalid email address"
            return false
        }

        emailLayout.error = null  // Clear any previous errors
        return true // Email is valid
    }

    private fun validatePassword(): Boolean {
        val `val`: String = passwordLayout.getEditText()?.getText().toString()
        val passwordVal = "^" +  //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +  //any letter
                "(?=.*[@#$%^&+=])" +  //at least 1 special character
                "(?=\\S+$)" +  //no white spaces
                ".{4,}" +  //at least 4 characters
                "$"
        if (`val`.isEmpty()) {
            passwordLayout.setError("Field cannot be empty")
            return false
        } else if (!`val`.matches(passwordVal.toRegex())) {
            passwordLayout.setError("Password is too weak")
            return false
        } else {
            passwordLayout.setError(null)
            passwordLayout.setErrorEnabled(false)
            return true
        }
    }
    private fun validatePhoneNo(): Boolean {
        val `val`: String = numeroLayout.getEditText()?.getText().toString()
        if (`val`.isEmpty()) {
            numeroLayout.setError("Field cannot be empty")
            return false
        } else {
            numeroLayout.setError(null)
            numeroLayout.setErrorEnabled(false)
            return true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
       // mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnLogIn =findViewById(R.id.btnLogIn)
        btnLogIn.setOnClickListener {
            val intent = Intent(this, Login_layout::class.java)
            startActivity(intent)
        }
        usernameLayout = findViewById(R.id.username)
        emailLayout = findViewById(R.id.email)
        numeroLayout = findViewById(R.id.numero)
        passwordLayout = findViewById(R.id.password)
        btnSignUp = findViewById(R.id.btnSignUp)

        auth = FirebaseAuth.getInstance()


        btnSignUp.setOnClickListener {
            val username = usernameLayout.editText?.getText().toString()
            val email = emailLayout.editText?.getText().toString()
            val numero = numeroLayout.editText?.getText().toString()
            val password = passwordLayout.editText?.getText().toString()
            System.out.println("username: $username")
            System.out.println("email: $email")
            System.out.println("numero: $numero")
            System.out.println("password: $password")
           if (validateUsername() or validatePassword() or validatePhoneNo() or validateEmail() or validateUsername()) {
               checkEmailExistence(username,email,numero ,password);

            }else{
                return@setOnClickListener
            }
        }

    }

    private fun checkEmailExistence(username: String, email: String, numero: String, password: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("users")
            .whereEqualTo("email", email) // Check for email field
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result.isEmpty) {
                        // Email doesn't exist, proceed with user creation
                        createUser(username, email, numero, password)
                    } else {
                        Toast.makeText(
                            this@SignUp,
                            "Email already exists!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Log.w("Signup", "Error checking email existence.", task.exception)
                    Toast.makeText(
                        this@SignUp,
                        "Error checking email. Please try again.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
    @SuppressLint("RestrictedApi")
    private fun createUser(username: String, email: String, numero: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val uid = user?.uid ?: return@addOnCompleteListener

                    val newUser = com.example.medical.data.User(
                        username,  // Position 1: username
                        email,     // Position 2: email
                        numero,    // Position 3: numero
                        password,  // Position 4: password
                        isNew = true // Position 5 (with default value for isNew)
                    )

                    val db = FirebaseFirestore.getInstance()
                    db.collection("users").document(uid).set(newUser)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Successfully added!", Toast.LENGTH_LONG).show()
                            Log.d("Signup", "User created and data stored in Firestore")

                            // Navigate to main activity or show success message
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_LONG).show()
                            // Handle signup failure
                        }
                } else {
                    // Handle signup failure
                    Log.w("Signup", "createUserWithEmailAndPassword failed.", task.exception)
                    Toast.makeText(this, "The email address is already in use by another account.", Toast.LENGTH_LONG).show()
                }
            }
            val intent = Intent(this, Login_layout::class.java)
             startActivity(intent)
        Toast.makeText(this, "Successfully added!", Toast.LENGTH_LONG).show()
    }
    private fun inputCheck(firstName: String, lastName: String,numero: String, passwordLayout: String): Boolean{
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(numero) && TextUtils.isEmpty(passwordLayout))
    }
}