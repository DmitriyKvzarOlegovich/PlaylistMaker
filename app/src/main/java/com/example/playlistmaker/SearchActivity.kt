package com.example.playlistmaker



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
//нажатие на поиск


        val buttonSearchBack = findViewById<Button>(R.id.buttonSearchBack)
        buttonSearchBack.setOnClickListener {
            onBackPressed()


        }
    }
}