package com.example.cardassignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity() {
    lateinit var startButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startButton = findViewById(R.id.start_button)

        startButton.setOnClickListener {
            val intent:Intent = Intent(this,CardActivity::class.java)
            startActivity(intent)

        }
    }
}