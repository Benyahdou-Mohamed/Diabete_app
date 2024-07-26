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

class SanguinActivity : AppCompatActivity() {
    private lateinit var Hdl_text: TextInputLayout

    private lateinit var Triglycerides_text: TextInputLayout
    private lateinit var Uree_text: TextInputLayout
    private lateinit var cholestérol_text: TextInputLayout // This might have a typo, consider ldl_text
    private lateinit var Creatinine_text: TextInputLayout
    private lateinit var Ldl_text: TextInputLayout
    private lateinit var suivantButton: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sanguin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Hdl_text = findViewById(R.id.Hdl_text)
        Triglycerides_text = findViewById(R.id.Triglycerides_text)
        Uree_text = findViewById(R.id.Uree_text)
        cholestérol_text = findViewById(R.id.cholesterol_text)
        Ldl_text = findViewById(R.id.ldl_text)
        Creatinine_text = findViewById(R.id.Creatinine_text)

        suivantButton = findViewById(R.id.btn_suivant)
        suivantButton.setOnClickListener {
            // Save data to SharedPreferences and navigate to the next activity
            saveDataAndNavigate()
        }

        // Set up a text change listener for all TextInputLayouts
        setupTextChangeListeners()
    }
    private fun setupTextChangeListeners() {
        val textFields = listOf(Hdl_text, Triglycerides_text, Uree_text, Ldl_text, Creatinine_text, cholestérol_text)
        for (textField in textFields) {
            textField.editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    // Enable the button if all text fields are not empty
                    suivantButton.isEnabled = isAllFieldsNotEmpty()
                }
            })
        }
    }
    private fun saveDataAndNavigate() {
        // Save data to SharedPreferences
        val sharedPreferences = getSharedPreferences("user_answers", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("hdl", Hdl_text.editText?.text.toString().trim())
        editor.putString("triglycerides", Triglycerides_text.editText?.text.toString().trim())
        editor.putString("uree", Uree_text.editText?.text.toString().trim())
        editor.putString("ldl", Ldl_text.editText?.text.toString().trim())
        editor.putString("creatinine", Creatinine_text.editText?.text.toString().trim())
        editor.putString("cholesterol", cholestérol_text.editText?.text.toString().trim())

        editor.apply()

        // Navigate to the next activity
        val intent = Intent(this, LoadActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun isAllFieldsNotEmpty(): Boolean {
        // Check if all TextInputLayouts contain text
        return Hdl_text.editText?.text?.isNotEmpty() == true &&
                Triglycerides_text.editText?.text?.isNotEmpty() == true &&
                Uree_text.editText?.text?.isNotEmpty() == true &&
                Ldl_text.editText?.text?.isNotEmpty() == true &&
                Creatinine_text.editText?.text?.isNotEmpty() == true &&
                cholestérol_text.editText?.text?.isNotEmpty() == true
    }
}