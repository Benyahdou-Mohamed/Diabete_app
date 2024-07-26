package com.example.medical

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActiveActivity : AppCompatActivity() {

    private lateinit var sedentaryButton: Button
    private lateinit var lightlyActiveButton: Button
    private lateinit var moderatelyActiveButton: Button
    private lateinit var veryActiveButton: Button
    private lateinit var extremelyActiveButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_active)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sedentaryButton = findViewById(R.id.btnSedentaire)
        lightlyActiveButton = findViewById(R.id.btnLegerement)
        moderatelyActiveButton = findViewById(R.id.btnModerementActive)
        veryActiveButton = findViewById(R.id.btnTresActive)
        extremelyActiveButton = findViewById(R.id.btnExtenement)
        sedentaryButton.setOnClickListener {
            storeAnswer("Sedentary")  // Replace with appropriate answer string
            val intent = Intent(this, BirthDateActivity::class.java)
            startActivity(intent)

        }
        lightlyActiveButton.setOnClickListener {
            storeAnswer("Lightly ")  // Replace with appropriate answer string
            val intent = Intent(this, BirthDateActivity::class.java)
            startActivity(intent)

        }
        moderatelyActiveButton.setOnClickListener {
            storeAnswer("Moderately ")  // Replace with appropriate answer string
            val intent = Intent(this, BirthDateActivity::class.java)
            startActivity(intent)

        }
        veryActiveButton.setOnClickListener {
            storeAnswer("Very ")  // Replace with appropriate answer string
            val intent = Intent(this, BirthDateActivity::class.java)
            startActivity(intent)

        }

    }
    private fun storeAnswer(answer: String) {
        // Implement logic to store the answer in your preferred method (e.g., SharedPreferences)
        val sharedPref = getSharedPreferences("user_answers", MODE_PRIVATE)

        with (sharedPref.edit()) {
            putString("active_answer", answer)
            apply()
        }
    }
}