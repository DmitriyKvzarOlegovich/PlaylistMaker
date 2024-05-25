package com.example.playlistmaker

import android.content.SharedPreferences
import com.google.gson.Gson

class SearchHistory {
    // чтение
    fun read(sharedPreferences: SharedPreferences): ArrayList<Track>? {
        val json = sharedPreferences.getString(EDIT_TEXT_KEY, null)// ?: return emptyArray()
        val jsonArray = Gson().fromJson(json, Array<Track>::class.java)
        return ArrayList(jsonArray.asList())
    }

    // запись
    fun write(sharedPreferences: SharedPreferences, histori: ArrayList<Track>) {
        val k = 10
        var tempHistory = ArrayList<Track>()
        for (i in histori.indices) {
            if (!tempHistory.contains(histori[histori.size - i - 1])) {
                tempHistory.add(histori[histori.size - i - 1])
                if (tempHistory.size == k) {
                    break
                }
            }
        }
        val json = Gson().toJson(tempHistory)
        sharedPreferences.edit()
            .putString(EDIT_TEXT_KEY, json)
            .apply()
    }

    // чтение
    fun readTheme(sharedPreferences: SharedPreferences): Boolean? {
        val json = sharedPreferences.getBoolean(EDIT_Theme_KEY, null == false)
        return json
    }

    // запись
    fun writeTheme(sharedPreferences: SharedPreferences, theme: Boolean) {
        sharedPreferences.edit()
            .putBoolean(EDIT_Theme_KEY, theme)
            .apply()
    }

}