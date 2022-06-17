package com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore

class DataStoreManager(private val context: Context) {

    companion object {
        private const val DATASTORE_NAME = "data_store"

        private val LOGIN_KEY = booleanPreferencesKey("login_key")
        private val USERID_KEY = intPreferencesKey("user_id")

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

        private val EMAIL = stringPreferencesKey("KEY_EMAIL")
        private val PASSWORD = stringPreferencesKey()("KEY_PASSWORD")
    }

    suspend fun loginUserData(
        email : String,
        password: String
    )
    {
        context.dataStore.edit {
            it[EMAIL] = email
            it[PASSWORD] = password
        }
    }
}