package com.example.playlistmaker


import android.app.Activity
import android.app.UiModeManager.MODE_NIGHT_YES
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_settings)
        val settingsBack = findViewById<TextView>(R.id.settingsBack)

        settingsBack.setOnClickListener {
            onBackPressed()


        }
//смена темы
        val themeSwitch = findViewById<androidx.appcompat.widget.SwitchCompat>(R.id.themeSwitch)

        val mode = AppCompatDelegate.getDefaultNightMode()
        if (mode == 1) {
            themeSwitch.text = getString(R.string.title_activity_light)
        } else {
            themeSwitch.text = getString(R.string.title_activity_dark)
            themeSwitch.isChecked = true
        }




        themeSwitch.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                true -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    themeSwitch.text = getString(R.string.title_activity_dark)
                }

                false -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    themeSwitch.text = getString(R.string.title_activity_light)
                }
            }
        }



   //Разшарить приложение

        val buttonShare = findViewById<TextView>(R.id.buttonShare)
        buttonShare.setOnClickListener {

            val email = arrayOf(getString(R.string.email_fo_send))
            val intent = Intent(Intent.ACTION_SEND).apply {
                type="text/plain"

                putExtra(Intent.EXTRA_TEXT, getString(R.string.send_to));

            }
            startActivity(Intent.createChooser(intent,getString(R.string.send_to)))

        }


//Отработка отправки ПИСЬМА в службу поддержки

        val sendEmail = findViewById<TextView>(R.id.buttonSupport)
        sendEmail.setOnClickListener {

            val email = arrayOf(getString(R.string.email_fo_send))
            val intent = Intent(Intent.ACTION_SENDTO).apply {



                data = Uri.parse("mailto:${getString(R.string.email_fo_send)}")
                putExtra(Intent.EXTRA_EMAIL, email)
                putExtra(Intent.EXTRA_TEXT , getString(R.string.thanks))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.send_play_list_message))
            }

            startActivity(Intent.createChooser(intent, null))

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

