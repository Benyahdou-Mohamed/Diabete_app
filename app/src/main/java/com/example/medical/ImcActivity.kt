package com.example.medical

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import me.bastanfar.semicirclearcprogressbar.SemiCircleArcProgressBar

class ImcActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var resultText: TextView
    private lateinit var suivantButton: TextView
    private lateinit var imcPercText: TextView
    private lateinit var imcCategoryText: TextView
    private lateinit var imcActuceText: TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imc)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        suivantButton = findViewById(R.id.btn_suivant)
        imcPercText= findViewById(R.id.imcPerc)
        imcCategoryText= findViewById(R.id.imctext)
        imcActuceText = findViewById(R.id.imcAstuce)
        sharedPreferences = getSharedPreferences("user_answers", MODE_PRIVATE)
        val weight = sharedPreferences.getString("weight_answer", "0")?.toInt()
        val height = sharedPreferences.getString("Height_answer", "")
        val heightInCm: Float? = height?.toIntOrNull()?.toFloat()
        val heightInMeters = heightInCm?.div(100.0f)
        System.out.println("weight: $weight")
        System.out.println("height: $height")
        System.out.println("heightf: $heightInMeters")
        val IMC = weight?.div(heightInMeters?.times(heightInMeters)!!)
        System.out.println("IMC: $IMC")
        val formattedIMC = String.format("%.1f", IMC)

        val bmiCategory: String = when (IMC!!) {

            in 18.5f..24.9f -> "Normal"
            in 25.0f..29.9f -> "Surpoids"
            in 30.0f..34.9f -> "Obeseté Moderérée"
            in 35.0f..40.0f -> "Obeseté sévere"
            else -> {
                if (IMC!! < 18.5f) "Maigreur" else "Obeseté morbite"
            }
        }
        imcPercText.text= " $formattedIMC"+"%"
        imcCategoryText.text=bmiCategory
        val progressBarColor = getProgressColor(bmiCategory)
        val astuce = getAstuce(bmiCategory)
        imcActuceText.text=astuce.toString()
        val progressBar = findViewById<SemiCircleArcProgressBar>(R.id.smc)
        progressBar.setPercent(30);
        progressBar.setPercentWithAnimation(IMC.toInt()+25);

        progressBar.setProgressBarColor(progressBarColor.toInt());
        progressBar.setProgressPlaceHolderColor(0xffE6E5E5.toInt());
        progressBar.setProgressBarWidth(60);
        progressBar.setProgressPlaceHolderWidth(70);

        val sharedPref = getSharedPreferences("user_answers", MODE_PRIVATE)

        with (sharedPref.edit()) {
            putString("imcCategory", bmiCategory)
            putString("imcPerc", formattedIMC)

            apply()
        }

        suivantButton.setOnClickListener {

            val intent = Intent(this, info2Activity::class.java)
            startActivity(intent)
            finish()
        }





    }
    fun getAstuce(bmiCategory: String): Comparable<Nothing> {
        val astuce = mapOf(
            "Normal" to "\t \t 1. Maintenir une alimentation équilibrée \n \t \t 2. Surveiller les portions \n \t \t 3. Faire de l’exercice régulièrement \n \t \t 4. Hydratation \n \t \t 5. Contrôle régulier du poids \n \t \t \n",  // Green (healthy range)

            "Surpoids" to "\t \t 1.Adopter une alimentation pauvre en calories \n \t \t 2. Augmenter l'apport en fibres \n \t \t 3.Contrôler les portions \n \t \t 4.Faire de l’exercice \n \t \t 5.Fixer des objectifs réalistes \n \t \t",  // Yellow (warning)

            "Obeseté Moderérée" to "\t \t 1.Suivre un régime hypocalorique. \n \t \t 2.Privilégier des aliments sains. \n \t \t 3.Augmenter l'activité physique. \n \t \t 4.Éviter les régimes à la mode. \n \t \t 5.Suivi médical. \\n \t \t \n",  // Red (obesity)
            "Obeseté sévere" to "\t \t 1.Planification des repas . \n \t \t 2.Réduction des portions . \n \t \t 3.Activité physique adaptée . \n \t \t 4.Éviter les aliments transformés \n \t \t 5.Support psychologique. \n \t \t ",  // Purple (severe obesity)

            "Maigreur" to "\t \t 1.Augmenter l'apport calorique \n \t \t 2.Manger plus fréquemment \n \t \t 3. Ajouter des protéines \n \t \t 4.Boire des boissons nutritives  \n \t \t 5. Faire de l'exercice  \n \t \t \n",  // Blue (underweight)

            "Obeseté morbite" to "\t \t 1.Suivi médical rigoureux. \n \t \t 2.Modification du comportement. \n \t \t 3.Activité physique encadrée . \n \t \t 4.Soutien psychologique. \n \t \t 5.Envisager des interventions médicales . \n \t \t"  // Purple (severe obesity)
        )
        return astuce[bmiCategory] ?: "0xff2ECC71" // Default gray for unknown categories
    }
    fun getProgressColor(bmiCategory: String): Long {
        val colorMap = mapOf(
            "Normal" to 0xff2ECC71,  // Green (healthy range)
            "Surpoids" to 0xffFF9800,  // Yellow (warning)
            "Obeseté Moderérée" to 0xffE74C3C,  // Red (obesity)
            "Obeseté sévere" to 0xffE43523,  // Purple (severe obesity)
            "Maigreur" to 0xff3498DB,  // Blue (underweight)
            "Obeseté morbite" to 0xff9B59B6  // Purple (severe obesity)
        )
        return colorMap[bmiCategory] ?: 0xff2ECC71 // Default gray for unknown categories
    }
}