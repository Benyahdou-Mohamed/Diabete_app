package com.example.medical

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DiabeteActivity3 : AppCompatActivity() {
    private lateinit var yearText: TextView
    private lateinit var textAnn: TextView
    private lateinit var textAnne: TextView
    private lateinit var numberPicker: NumberPicker
    private lateinit var btnSuivant: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_diabete3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        yearText = findViewById(R.id.yearText)


        numberPicker = findViewById(R.id.number_picker)
        btnSuivant = findViewById(R.id.btn_suivant)
        numberPicker.setMinValue(1960); // Set minimum value (can be any integer)
        numberPicker.setMaxValue(2024);
        numberPicker.setValue(2024);
        for (i in 0 until numberPicker.childCount) {
            val child = numberPicker.getChildAt(i)
            if (child is TextView) {
                child.textSize = 24f // Set text size
                break  // Exit the loop after finding the TextView for numbers
            }
        }
        numberPicker.setOnValueChangedListener { picker, oldValue, newValue ->
            yearText.text = " $newValue"

            storeAnswer(newValue.toString())
            btnSuivant.isEnabled = true
        }
        btnSuivant.setOnClickListener {
            val intent = Intent(this, SanguinActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun storeAnswer(answer: String) {
        // Implement logic to store the answer in your preferred method (e.g., SharedPreferences)
        val sharedPref = getSharedPreferences("user_answers", MODE_PRIVATE)

        with (sharedPref.edit()) {
            putString("Diabetes_qst_4", answer)
            apply()
        }
    }
}