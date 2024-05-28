package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate


const val EDIT_Theme_KEY = "key_for_edit_theme"
const val PRACTICUM_EXAMPLE_PREFERENCES = "practicum_example_preferences"
const val EDIT_TEXT_KEY = "key_for_edit_text"


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val sharedPrefs = getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)
        if (SearchHistory().readTheme(sharedPrefs) == true) {
            darkTheme = true
        }
        switchTheme(darkTheme)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES

            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}