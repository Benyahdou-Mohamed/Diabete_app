package com.example.medical

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import me.bastanfar.semicirclearcprogressbar.SemiCircleArcProgressBar

class Intro : AppCompatActivity() {
    private lateinit var btnLogOut: Button
    private lateinit var btnNext: Button

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        btnLogOut = findViewById(R.id.btnLogout)
        btnNext = findViewById(R.id.btnNext)
        btnLogOut.setOnClickListener {
            logout()
            val intent = Intent(this, Login_layout::class.java)
            startActivity(intent)
        }
        btnNext.setOnClickListener {
            val intent = Intent(this, infoActivity::class.java)
            startActivity(intent)
        }
    }

}