package com.example.medical

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout

class DiabeteActivity : AppCompatActivity() {
    private lateinit var questionTextView: TextView
    private lateinit var unitText: TextView
    private lateinit var answerEditText: TextInputLayout
    private lateinit var suivantButton: TextView
    private var currentQuestionIndex: Int = 0
    private var currentUnitIndex: Int = 0
    private val questions = listOf(
        "Quelle est votre pourcentage d'Hba1c?",
        "Quelle est votre taux de glycémie à jeûn ?",
    )
    private val unit = listOf(
        "%",
        "  g/l",
    )
    private val sharedPrefName = "user_answers"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_diabete)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        questionTextView = findViewById(R.id.question_text)
        answerEditText = findViewById(R.id.answer_edit_text)
        suivantButton = findViewById(R.id.btn_suivant)
        unitText = findViewById(R.id.text_unit)
        updateQuestion()
        answerEditText.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Enable the button if the EditText is not empty
                suivantButton.isEnabled = !s.isNullOrEmpty()
            }
        })
        suivantButton.setOnClickListener {
            val answer = answerEditText.editText?.text.toString().trim()

            // Store answer in SharedPreferences
            val sharedPreferences = getSharedPreferences(sharedPrefName, MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("Diabetes_qst_$currentQuestionIndex", answer)
            editor.apply()

            // Check if more questions exist
            if (currentQuestionIndex < questions.lastIndex) {
                currentQuestionIndex++
                currentUnitIndex++
                updateQuestion()
                answerEditText.editText?.text?.clear()
            } else {
                // No more questions, navigate to next activity (optional)
                val intent = Intent(this, DiabeteActivity2::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    private fun updateQuestion() {
        questionTextView.text = questions[currentQuestionIndex]
        unitText.text = unit[currentUnitIndex]
    }
}