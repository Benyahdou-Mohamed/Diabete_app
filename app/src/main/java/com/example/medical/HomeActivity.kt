package com.example.medical

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel

import kotlin.random.Random


class HomeActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userPref: SharedPreferences
    private lateinit var imcPercText: TextView
    private lateinit var imcCategoryText: TextView
    private lateinit var imcDescText: TextView
    private lateinit var imcDescText1: TextView
    private lateinit var heightText: TextView
    private lateinit var weightText: TextView
    private lateinit var layoutColor: LinearLayout
    private lateinit var layoutColor2: LinearLayout
    private lateinit var btnLogout: ImageView

    private var imcInt :Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sharedPreferences = getSharedPreferences("user_answers", MODE_PRIVATE)
        userPref = getSharedPreferences("login_pref", MODE_PRIVATE)
        imcPercText= findViewById(R.id.imcPerc)
        imcDescText= findViewById(R.id.imcDesc)
        imcDescText1= findViewById(R.id.imcDesc1)
        imcCategoryText= findViewById(R.id.imcCateg)
        heightText= findViewById(R.id.heightText)
        weightText= findViewById(R.id.weightText)
        layoutColor=findViewById(R.id.imcLayout1)
        layoutColor2=findViewById(R.id.imcLayout2)
        btnLogout=findViewById(R.id.btnLogout)
        val height = sharedPreferences.getString("Height_answer", "")
        val weight = sharedPreferences.getString("weight_answer", "0")
        val imcPerc = sharedPreferences.getString("imcPerc", "0")
        val imcCateg = sharedPreferences.getString("imcCategory", "0")

        val colorMap = mapOf(
            "Normal" to "#068251",
            "Surpoids" to "#FF9800",
            "Obeseté Moderérée" to "#E74C3C",
            "Obeseté sévere" to "#E43523",
            "Maigreur" to "#3498DB",
            "Obeseté morbite" to "#9B59B6"
        )
        val percentage = mapOf(
            "Normal" to (30..50).random(),
            "Surpoids" to (50..60).random(),
            "Obeseté Moderérée" to (60..75).random(),
            "Obeseté sévere" to (75..90).random(),
            "Maigreur" to (0..50).random(),
            "Obeseté morbite" to (90..97).random()
        )

        val color= colorMap[imcCateg] ?: "#2ECC71"
        val perc= percentage[imcCateg] ?: "60"
        val newColor = Color.parseColor(color)
        val radiusInDp = 10 // Replace with your desired radius

        val roundedBackground = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 30.0F // Convert dp to pixels
            setColor(newColor) // Replace with your desired color
        }
        layoutColor.background = roundedBackground
        layoutColor2.background = roundedBackground

        btnLogout.setOnClickListener {
            logout()
            val intent = Intent(this, Login_layout::class.java)
            startActivity(intent)
        }
        imcPercText.text= "$imcPerc"+"%"
        imcCategoryText.text="Votre Imc est :"
        imcDescText.text="$imcCateg"
        heightText.text="$height"+"Cm"
        weightText.text="$weight"+"Kg"
        imcDescText1.text="$perc"+"%"
        val imageList = ArrayList<SlideModel>() // Create image list

        imageList.add(SlideModel(R.drawable.ad1))
        imageList.add(SlideModel(R.drawable.med3))
        imageList.add(SlideModel(R.drawable.med4))
        imageList.add(SlideModel(R.drawable.banner_large))


        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)



    }

    fun randomfun(minValue: Int, maxValue: Int): Int {
        require(minValue < maxValue) { "minValue must be less than maxValue" }

        val randomValue = Random.nextInt(minValue, maxValue)
        return randomValue
    }
    private fun logout() {
        // Clear user authentication state

        val sharedPref = getSharedPreferences("login_pref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.remove("auth_token") // If using secure storage
        editor.apply()
        // Navigate back to login screen
        val intent = Intent(this, Login_layout::class.java)
        startActivity(intent)
        finish()
    }
}