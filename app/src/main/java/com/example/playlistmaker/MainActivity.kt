package com.example.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
//import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//нажатие на поиск
        val buttonSearchL = findViewById<Button>(R.id.buttonSearchL)

        val imageClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                val displayIntent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(displayIntent)


            }
        }

        buttonSearchL.setOnClickListener(imageClickListener)
// нажатие на медия
        val buttonMediaL = findViewById<Button>(R.id.buttonMediaL)

        buttonMediaL.setOnClickListener {
            val displayIntent = Intent(this, MediaActivity::class.java)
            startActivity(displayIntent)

        }
// нажатие на НАСТРОЙКИ
        val buttonSettingsL = findViewById<Button>(R.id.buttonSettingsL)

        buttonSettingsL.setOnClickListener {
            val displayIntent = Intent(this, SettingsActivity::class.java)
            startActivity(displayIntent)

        }

    }
}



