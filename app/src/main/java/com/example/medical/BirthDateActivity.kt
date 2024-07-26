package com.example.medical

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class BirthDateActivity : AppCompatActivity() {
    private lateinit var buttonPickDate: Button
    private lateinit var birthText: TextView
    private lateinit var buttonSuivant: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_birth_date)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sharedPref = getSharedPreferences("user_answers", MODE_PRIVATE)
        val answer = sharedPref.getString("question1_answer", "")
        System.out.println("Question 1 Answer: $answer")
        buttonPickDate = findViewById(R.id.button_pick_date)
        birthText = findViewById(R.id.birthText)
        buttonSuivant = findViewById(R.id.btn_suivant)
        buttonPickDate.setOnClickListener {
            // Launch the date picker dialog
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth ->
                    // Handle the selected date here
                    val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                    val birthDate = Calendar.getInstance()
                    birthDate.set(year, monthOfYear, dayOfMonth)
                    val age = calculateAge(birthDate)
                    birthText.text = selectedDate
                    storeAnswer(age.toString())
                    buttonSuivant.isEnabled = true

                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH))
            datePicker.datePicker.maxDate = now.timeInMillis
            datePicker.show()
        }
        buttonSuivant.setOnClickListener {
            val intent = Intent(this, HeightActivity::class.java)
            startActivity(intent)
        }
    }
    private fun calculateAge(birthDate: Calendar): Int {
        val now = Calendar.getInstance()
        var age = now.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
        if (now.get(Calendar.MONTH) < birthDate.get(Calendar.MONTH) ||
            (now.get(Calendar.MONTH) == birthDate.get(Calendar.MONTH) && now.get(Calendar.DAY_OF_MONTH) < birthDate.get(Calendar.DAY_OF_MONTH))) {
            age -= 1
        }
        return age
    }
    private fun storeAnswer(answer: String) {
        // Implement logic to store the answer in your preferred method (e.g., SharedPreferences)
        val sharedPref = getSharedPreferences("user_answers", MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString("Age_answer", answer)
            apply()
        }
    }
}