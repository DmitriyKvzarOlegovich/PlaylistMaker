package com.example.playlistmaker


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val settingsBack = findViewById<TextView>(R.id.settingsBack)

        settingsBack.setOnClickListener {
            onBackPressed()


        }

    }


}