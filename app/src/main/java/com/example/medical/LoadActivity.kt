package com.example.medical

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import android.os.Handler
import android.widget.TextView


class LoadActivity : AppCompatActivity() {
    private lateinit var animation1: LottieAnimationView
    private lateinit var animation2: LottieAnimationView
    private lateinit var text1: TextView
    private lateinit var text2: TextView
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_load)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        animation1 = findViewById(R.id.animation1)
        animation2 = findViewById(R.id.animation2)
        text1 = findViewById(R.id.text1)
        text2 = findViewById(R.id.text2)
        handler = Handler()

        // Start animation1 automatically
        animation1.playAnimation()

        handler.postDelayed({
            // Make animation1 invisible after 3 seconds
            animation1.visibility = View.INVISIBLE

            // Start animation2 (with autoPlay disabled to prevent immediate start)
            animation2.visibility = View.VISIBLE
            text1.text= "Analyze Terminer"
            text2.text=""
            animation2.playAnimation()

            // Delay for 3 seconds before transitioning to next screen
            handler.postDelayed({
                // Replace with your logic to launch next activity
                val intent = Intent(this, LoadingActivity::class.java)
                startActivity(intent)
                finish() // Optionally finish LoadActivity
            }, 3000) // Delay for 3 seconds
        }, 7000) // Delay for 7 seconds

    }
}