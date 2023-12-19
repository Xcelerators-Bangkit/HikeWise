package com.example.hikewise.pref.user

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preference")
class UserPreference private constructor(private val dataStore: DataStore<Preferences>){

    suspend fun saveUser(user: User){
        dataStore.edit { preferences ->
            preferences[USER_KEY] = user.email
            preferences[USER_PW] = user.password
            Log.d("UserPreference", user.email)
        }
    }

    val getUserEmail: Flow<String?> = dataStore.data.map { preferences ->
        preferences[USER_KEY]
    }

    val getUserPassword: Flow<String?> = dataStore.data.map { preferences ->
        preferences[USER_PW]
    }


    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null
        private val USER_KEY = stringPreferencesKey("user_email")
        private val USER_PW =  stringPreferencesKey("password")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference{
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}