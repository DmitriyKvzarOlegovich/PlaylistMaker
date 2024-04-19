package com.example.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MediaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)

//нажатие на поиск
        val buttonMediaBack = findViewById<Button>(R.id.buttonMediaBack)
        buttonMediaBack.setOnClickListener {
            onBackPressed()


        }
    }
}
