package com.example.medical

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.medical.data.Medical
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


class LoadingActivity : AppCompatActivity() {
    private lateinit var resultText: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userPref: SharedPreferences
    private lateinit var suivantButton: TextView
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loading)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        suivantButton = findViewById(R.id.btn_suivant)
        resultText = findViewById(R.id.result_text)
        sharedPreferences = getSharedPreferences("user_answers", MODE_PRIVATE)
        userPref = getSharedPreferences("login_pref", MODE_PRIVATE)
        // Get all key-value pairs from SharedPreferences
        val allEntries = sharedPreferences.all

        // Build a string to display all stored values
        val stringBuilder = StringBuilder()
        if (allEntries.isEmpty()) {
            stringBuilder.append("SharedPreferences is empty.")
        } else {
            for ((key, value) in allEntries) {
                stringBuilder.append("$key: $value\n")
                Log.d("SharedPreferences", "$key: $value")
            }
        }
        val diabetesQst0 = sharedPreferences.getString("Diabetes_qst_0", "0")
        val diabetesQst1 = sharedPreferences.getString("Diabetes_qst_1", "0")
        val diabetesQst4 = sharedPreferences.getString("Diabetes_qst_4", "0")
        val diabetesQst3 = sharedPreferences.getString("Diabetes_qst_3", "")
        val heightAnswer = sharedPreferences.getString("Height_answer", "0")
        val triglycerides = sharedPreferences.getString("triglycerides", "0")
        val uree = sharedPreferences.getString("uree", "0")
        val hdl = sharedPreferences.getString("hdl", "0") // Assuming hdl is an integer value
        val ageAnswer = sharedPreferences.getString("Age_answer", "0")
        val weight = sharedPreferences.getString("weight_answer", "0")
        val creatinine = sharedPreferences.getString("creatinine", "0")
        val ldl = sharedPreferences.getString("ldl", "0")
        val cholesterol = sharedPreferences.getString("cholesterol", "0")
        val genderAnswer = sharedPreferences.getString("gender_answer", "")
        val height = sharedPreferences.getString("Height_answer", "")
        val userId = userPref.getString("user_id","0")
        //System.out.println("userId: $userId")

        val medicalData = Medical(0,
            userId.toString(), genderAnswer.toString(), height?.toInt() ?: 0,ageAnswer?.toInt() ?: 0,
            weight?.toInt() ?: 0 , diabetesQst3.toString(),diabetesQst1?.toFloat() ?: 0,
            diabetesQst0?.toFloat() ?: 0,diabetesQst4?.toFloat() ?: 0,
            cholesterol?.toFloat() ?: 0,creatinine?.toFloat() ?: 0,uree?.toFloat() ?: 0,
            triglycerides?.toFloat() ?: 0,hdl?.toFloat() ?: 0,ldl?.toFloat() ?: 0)

        saveMedicalReport(userId.toString(), medicalData)


        resultText.text = stringBuilder.toString()
        suivantButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        goHome()
    }
    private fun goHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
    private fun saveMedicalReport(userId: String, medicalData: Medical) {
        val database = FirebaseDatabase.getInstance().reference  // Get the root reference of the Realtime Database
        val reportsRef = database.child("medical_reports").child(userId)  // Create reference to "medical_reports" child node

        // Push the medical data as a new child node with a unique key
        reportsRef.push().setValue(medicalData)
            .addOnSuccessListener {
                Log.d("RealtimeDatabase", "Medical report saved successfully!")
            }
            .addOnFailureListener { exception ->
                Log.w("RealtimeDatabase", "Error saving medical report: ", exception)

            }}
}