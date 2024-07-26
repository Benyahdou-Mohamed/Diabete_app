package com.example.medical

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class GenderActivity : AppCompatActivity() {
    private lateinit var btnWomen: TextView
    private lateinit var btnMen: TextView
    private lateinit var btnNext: TextView

    //private val defaultColor = ContextCompat.getColor(applicationContext, R.color.black)// Replace with your default color resource
    //private val clickedColor = ContextCompat.getColor(applicationContext, R.color.Green)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gender)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnWomen = findViewById(R.id.btnWomen)
        btnMen = findViewById(R.id.btnMen)
        //btnNext = findViewById(R.id.btnNext)


        btnWomen.setOnClickListener {
            storeAnswer("Women")
            val intent = Intent(this, BirthDateActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Féminin button clicked!", Toast.LENGTH_SHORT).show()
        }

        btnMen.setOnClickListener {
            storeAnswer("Men")
            val intent = Intent(this, BirthDateActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Masculin button clicked!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun storeAnswer(answer: String) {
        // Implement logic to store the answer in your preferred method (e.g., SharedPreferences)
        val sharedPref = getSharedPreferences("user_answers", MODE_PRIVATE)

        with (sharedPref.edit()) {
            putString("gender_answer", answer)
            apply()
        }
    }

}