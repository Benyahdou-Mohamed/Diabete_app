package com.example.medical

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DiabeteActivity2 : AppCompatActivity() {

    private lateinit var btnOui: TextView
    private lateinit var btnNon: TextView
    private lateinit var btnNext: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_diabete2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnOui = findViewById(R.id.btnOui)
        btnNon = findViewById(R.id.btnNon)
        //btnNext = findViewById(R.id.btnNext)
        btnOui.setOnClickListener {
            storeAnswer("Oui")
            val intent = Intent(this, DiabeteActivity3::class.java)
            startActivity(intent)
        }

        btnNon.setOnClickListener {
            storeAnswer("Non")
            val intent = Intent(this, DiabeteActivity3::class.java)
            startActivity(intent)

        }
    }
    private fun storeAnswer(answer: String) {
        // Implement logic to store the answer in your preferred method (e.g., SharedPreferences)
        val sharedPref = getSharedPreferences("user_answers", MODE_PRIVATE)

        with (sharedPref.edit()) {
            putString("Diabetes_qst_3", answer)
            apply()
        }
    }
}