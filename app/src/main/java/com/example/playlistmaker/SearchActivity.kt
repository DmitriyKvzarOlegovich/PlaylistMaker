package com.example.playlistmaker

import android.content.Context

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher

import android.view.inputmethod.InputMethodManager

import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible

class SearchActivity : AppCompatActivity() {
    private var searchInputEditText: String? = AMOUNT_DEF

    companion object {
        private const val PRODUCT_AMOUNT = "inputEditText"
        private const val AMOUNT_DEF = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

//нажатие на поиск Возврат в предидущее меню
        val buttonsearchBack = findViewById<TextView>(R.id.searchBack)
        buttonsearchBack.setOnClickListener {
            onSaveInstanceState(Bundle())
            onBackPressed()
        }

//Работа с поиском
        //val linearLayout = findViewById<LinearLayout>(R.id.containerList)
        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        clearButton.setOnClickListener {
            inputEditText.setText("")
            inputEditText.requestFocus()
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
            clearButton.isVisible = false
        }
        inputEditText.setText(searchInputEditText)
        inputEditText.requestFocus()
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // empty
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    clearButton.isVisible = false
                } else {
                    searchInputEditText = s.toString()
                    clearButton.isVisible = true
                }
            }
            override fun afterTextChanged(s: Editable?) {
                onSaveInstanceState(Bundle())  // empty
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
    }

//Переопределяем сохранение зачения текстового поля ввода
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putString(PRODUCT_AMOUNT, searchInputEditText)
    }
//Переопределяем востановление зачения текстового поля ввода
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchInputEditText = savedInstanceState.getString(PRODUCT_AMOUNT)
    }
}

