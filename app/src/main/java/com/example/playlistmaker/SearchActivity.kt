package com.example.playlistmaker


import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast


class SearchActivity : AppCompatActivity() {
    private var searchInputEditText: String? = AMOUNT_DEF

    companion object {
        const val PRODUCT_AMOUNT = "inputEditText"
        const val AMOUNT_DEF = ""
    }


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
//нажатие на поиск
        // Значение savedInstanceState равно null в том случае, если activity нечего восстанавливать.
        // То есть не произошло ситуации, при которой необходимо сохранить состояние
        if (savedInstanceState != null) {
            searchInputEditText = savedInstanceState.getString(PRODUCT_AMOUNT, AMOUNT_DEF)


        } else {

            //searchInputEditText = savedInstanceState?.getString(PRODUCT_AMOUNT)

        }
        //inputEditText.settext(searchInputEditText)

        val buttonsearchBack = findViewById<TextView>(R.id.searchBack)
        buttonsearchBack.setOnClickListener {
            onSaveInstanceState(Bundle())
            onBackPressed()


        }
        //Работа с поиском


        val linearLayout = findViewById<LinearLayout>(R.id.containerList)
        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)

        clearButton.setOnClickListener {
            inputEditText.setText("")
            clearButton.visibility = clearButtonVisibility("")
        }



        inputEditText.setText(searchInputEditText)


        //inputEditText.getBackground().setColorFilter(theme.colorSecondary, PorterDuff.Mode.SRC_ATOP);

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
                    clearButton.visibility = clearButtonVisibility("")
                    //linearLayout.setBackgroundColor(getColor(R.color.white))
                } else {
                    searchInputEditText = s.toString()
                    //Toast.makeText(this@SearchActivity, "Получаем строку", Toast.LENGTH_SHORT)
                    clearButton.visibility = clearButtonVisibility(s)
                }

            }

            override fun afterTextChanged(s: Editable?) {
                onSaveInstanceState(Bundle())  // empty
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
        // отработкаа запоминания
        //searchInputEditText=inputEditText.toString()
        //onSaveInstanceState(Bundle.p(putString(inputEditText.toString())))
        //onSaveInstanceState(Bundle())
    }
//Исчезновение кнопки удаления текста
    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

//Переопределяем сохранение зачения текстового поля ввода
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putString(PRODUCT_AMOUNT, searchInputEditText)
    }




    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Возвращаем сохраненное значение
        searchInputEditText = savedInstanceState.getString(PRODUCT_AMOUNT)

    }
}

