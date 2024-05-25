package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val settingsBack = findViewById<TextView>(R.id.settingsBack)
        val sharedPrefs = getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)
        val themeSwitch = findViewById<SwitchMaterial>(R.id.themeSwitcher)
// заполнение в соответствии с темой

        when (darkTheme) {
            true -> {
                themeSwitch.text = getString(R.string.title_activity_dark)
                themeSwitch.isChecked = true
            }

            false -> {
                themeSwitch.text = getString(R.string.title_activity_light)
                themeSwitch.isChecked = false
            }
        }
        //меню назад
        settingsBack.setOnClickListener {
            onBackPressed()
        }

//смена темы
        themeSwitch.setOnCheckedChangeListener { _, checked ->
            (applicationContext as App).switchTheme(checked)
            SearchHistory().writeTheme(sharedPrefs, checked)
        }


//Разшарить приложение
        val buttonShare = findViewById<TextView>(R.id.buttonShare)
        buttonShare.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, getString(R.string.send_to));
                startActivity(Intent.createChooser(this, getString(R.string.send_to)))
            }
        }

//Отработка отправки ПИСЬМА в службу поддержки
        val sendEmail = findViewById<TextView>(R.id.buttonSupport)
        sendEmail.setOnClickListener {
            Intent(Intent.ACTION_SENDTO).apply {
                val email = arrayOf(getString(R.string.email_fo_send))
                data = Uri.parse("mailto:${getString(R.string.email_fo_send)}")
                putExtra(Intent.EXTRA_EMAIL, email)
                putExtra(Intent.EXTRA_TEXT, getString(R.string.thanks))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.send_play_list_message))
                startActivity(Intent.createChooser(this, null))
            }
        }

//Открытие пользовательского соглашения
        val userAgreement = findViewById<TextView>(R.id.buttonUser)
        userAgreement.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.userAgreement)))
            startActivity(intent)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
    }
}

