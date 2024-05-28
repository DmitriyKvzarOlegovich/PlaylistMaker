package com.example.playlistmaker

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.isVisible


class SearchActivity : AppCompatActivity() {
    private var searchInputEditText: String? = AMOUNT_DEF
    private lateinit var placeholderMessage: TextView
    private lateinit var placeholderImage: ImageView
    private lateinit var buttonReBoot: Button
    private var searghtext: String = ""
    private var listTrack = ArrayList<Track>()
    private lateinit var rvTrack: RecyclerView
    private lateinit var buttonCleanHistory: Button
    private lateinit var rvHistory: RecyclerView
    private lateinit var layoutListFace: LinearLayout
    private lateinit var layoutTrable: LinearLayout
    private lateinit var layoutHistory: LinearLayout

    companion object {
        private const val PRODUCT_AMOUNT = "inputEditText"
        private const val AMOUNT_DEF = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        placeholderMessage = findViewById(R.id.placeholderMessageTrable)
        placeholderImage = findViewById(R.id.placeholderImage)
        buttonReBoot = findViewById(R.id.buttonReBoot)
        rvTrack = findViewById(R.id.trackListFace)
        rvHistory = findViewById(R.id.trackListHistori)
        buttonCleanHistory = findViewById(R.id.buttonCleanHistory)
        layoutHistory = findViewById(R.id.containerHistori)
        layoutTrable = findViewById(R.id.containerNot)
        layoutListFace = findViewById(R.id.containerListFace)
        val sharedPrefs = getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)
        rvTrack.layoutManager = LinearLayoutManager(this)
        val inputEditText = findViewById<EditText>(R.id.inputEditText)
//Считываем историю


        if (SearchHistory().read(sharedPrefs)?.isEmpty() == false) {
            layoutHistory.isVisible = true
            layoutListFace.isVisible = false
            rvHistory.adapter = TrackAdapter(SearchHistory().read(sharedPrefs))
        } else {
            inputEditText.requestFocus()
            layoutListFace.isVisible = true
            layoutHistory.isVisible = false
        }
// Отработка нажатия на очистку истории
        buttonCleanHistory.setOnClickListener {
            //весь список в память
            layoutHistory.isVisible = false
            layoutListFace.isVisible = true
            HistorylistTrack.clear()
            SearchHistory().write(sharedPrefs, HistorylistTrack)
            inputEditText.requestFocus()
        }

//нажатие на поиск Возврат в предидущее меню
        val buttonsearchBack = findViewById<TextView>(R.id.searchBack)
        buttonsearchBack.setOnClickListener {
            onSaveInstanceState(Bundle())
            onBackPressed()
            //весь список в память
            SearchHistory().write(sharedPrefs, HistorylistTrack)
        }
//нажатие отработка повторного запроса плей листа
        buttonReBoot.setOnClickListener {
            listTrack = fillTrackList(searghtext)
            rvTrack.adapter?.notifyDataSetChanged()
        }
//Работа с поиском
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        // Очистка поля ввода
        clearButton.setOnClickListener {
            inputEditText.setText("")
            inputEditText.requestFocus()
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
            clearButton.isVisible = false
            listTrack.clear()
            rvTrack.adapter?.notifyDataSetChanged()
            inputEditText.requestFocus()
            layoutTrable.isVisible = false
        }
        inputEditText.setText(searchInputEditText)
        val simpleTextWatcher =
            object : TextWatcher { // не разобрался как сделать слушателя без создания обйекта
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // empty
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    layoutTrable.visibility = View.GONE
                    layoutHistory.visibility =
                        if (inputEditText.hasFocus() && s?.isEmpty() == true && HistorylistTrack.size > 0) View.VISIBLE else View.GONE
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
        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // ВЫПОЛНЯЙТЕ ПОИСКОВЫЙ ЗАПРОС ЗДЕСЬ
                //Сюда пришли если нажали кнопку доне
                searghtext = inputEditText.text.toString()
                listTrack = fillTrackList(searghtext)
                rvTrack.layoutManager = LinearLayoutManager(this)
                rvTrack.adapter = TrackAdapter(listTrack)
                //true
            }
            false
        }
    }

    //Переопределяем сохранение зачения текстового поля ввода
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PRODUCT_AMOUNT, searchInputEditText)
    }

    //Переопределяем востановление зачения текстового поля ввода
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchInputEditText = savedInstanceState.getString(PRODUCT_AMOUNT)
    }

    fun fillTrackList(text: String?): ArrayList<Track> {
        val iTunesBaseUrl = "https://itunes.apple.com"
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(iTunesBaseUrl).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val itunesService = retrofit.create(iTunesAPI::class.java)
        val temp: Call<TrackResponse> = itunesService.search(text)
        temp.enqueue(object : Callback<TrackResponse> {
            override fun onResponse(
                call: Call<TrackResponse>,
                response: Response<TrackResponse>
            ) {
                Log.d("TRANSLATION_LOG", "Status code: ${response.code()}")
                if (response.code() == 200) { //полученн конкретный ответ
                    listTrack.clear()
                    if (response.body()?.results?.isNotEmpty() == true) {
                        listTrack.addAll(response.body()?.results!!)
                        rvTrack.adapter?.notifyDataSetChanged()
                    }
                    if (listTrack.isEmpty()) { //Если ответ получен но он пустой
                        showMessage(getString(R.string.nothing_found), "")
                    } else {
                        showMessage("", "")
                    }
                } else { //ошибка сети ответа нет
                    showMessage(
                        getString(R.string.something_went_wrong),
                        response.code().toString()
                    )
                }
            }

            override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                showMessage(getString(R.string.something_went_wrong), t.message.toString())
            }
        })
        return listTrack
    }

    fun showMessage(text: String, additionalMessage: String) {
        layoutHistory.isVisible = false
        layoutListFace.isVisible = false
        if (text.isNotEmpty()) {
            //блок ничего не найдено
            layoutTrable.isVisible = true
            placeholderMessage.text = text
            placeholderImage.setImageDrawable(getDrawable(R.drawable.not_found))
            buttonReBoot.isVisible = false
            listTrack.clear()
            // rvTrack.adapter?.notifyDataSetChanged()

            if (additionalMessage.isNotEmpty()) {
                //блок проблем с интернетом
                placeholderMessage.text = getString(R.string.something_went_wrong)
                placeholderImage.setImageDrawable(getDrawable(R.drawable.not_net))
                buttonReBoot.isVisible = true
                listTrack.clear()
                // rvTrack.adapter?.notifyDataSetChanged()
            }
        } else {
            //блок  очистки поля для
            layoutTrable.isVisible = false
            layoutListFace.isVisible = true
        }
    }
}