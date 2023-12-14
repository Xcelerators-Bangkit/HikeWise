package com.example.hikewise.pref.weatherprefs

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SharedPref internal constructor(private val context: Context){

    companion object {
        private const val SHARED_PREF_NAME = "my_prefs"
        private const val KEY_CITY = "city"

        @SuppressLint("StaticFieldLeak")
        private var instanse: SharedPref? = null

        fun getInstance(context: Context): SharedPref {
            if (instanse == null) {
                instanse = SharedPref(context.applicationContext)
            }
            return instanse!!
        }


    }

    private val prefs : SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    @SuppressLint("CommitPrefEdits")
    fun setValue(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getValue(key: String): String? {
        return prefs.getString(key, null)
    }

    fun setValueOrNull(key: String?, value: String?) {
        if (key != null && value != null) {
            prefs.edit().putString(key, value).apply()
        }
    }

    fun getValueOrNull(key: String?): String? {
        if (key != null) {
            return prefs.getString(key, null)
        }
        return null
    }

    fun clearCityValue() {
        prefs.edit().remove(KEY_CITY).apply()
    }
}