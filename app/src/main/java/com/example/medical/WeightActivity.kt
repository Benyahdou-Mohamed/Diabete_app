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

class WeightActivity : AppCompatActivity() {
    private lateinit var number_picker: NumberPicker
    private lateinit var weightText: TextView
    private lateinit var kgText: TextView
    private lateinit var buttonSuivant: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_weight)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sharedPref = getSharedPreferences("user_answers", MODE_PRIVATE)
        val answer = sharedPref.getString("Height_answer", "")
        System.out.println("height Answer: $answer")
        kgText =findViewById(R.id.textkg)
        buttonSuivant = findViewById(R.id.btn_suivant)
        weightText = findViewById(R.id.weightText)
        number_picker =findViewById(R.id.number_picker)
        number_picker.setMinValue(45); // Set minimum value (can be any integer)
        number_picker.setMaxValue(230);
        number_picker.setValue(78);
        for (i in 0 until number_picker.childCount) {
            val child = number_picker.getChildAt(i)
            if (child is TextView) {
                child.textSize = 24f // Set text size
                break  // Exit the loop after finding the TextView for numbers
            }
        }
        number_picker.setOnValueChangedListener { picker, oldValue, newValue ->
            weightText.text = " $newValue"
            kgText.text = "Kg"
            storeAnswer(newValue.toString())
            buttonSuivant.isEnabled = true
        }
        buttonSuivant.setOnClickListener {
            val intent = Intent(this, ImcActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun storeAnswer(answer: String) {
        // Implement logic to store the answer in your preferred method (e.g., SharedPreferences)
        val sharedPref = getSharedPreferences("user_answers", MODE_PRIVATE)

        with (sharedPref.edit()) {
            putString("weight_answer", answer)
            apply()
        }
    }
}