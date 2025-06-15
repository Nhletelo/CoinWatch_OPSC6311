package com.example.CoinWatch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Launcher : AppCompatActivity() {
override fun onCreate(savedInstanceState: Bundle?)
{
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_launcher)
    // Find button by ID

    val btnGetStarted = findViewById<Button>(R.id.button5)

    // Set click listener to navigate to MainActivity


    btnGetStarted.setOnClickListener{
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(this@Launcher , "Opening Main page", Toast.LENGTH_LONG).show()
    }
}
}