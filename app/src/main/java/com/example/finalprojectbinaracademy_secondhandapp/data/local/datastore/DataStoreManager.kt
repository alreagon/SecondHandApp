package com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DataStoreManager(private val context: Context) {
    suspend fun setLoginStatus() {
        context.dataStore.edit { pref ->
            pref[LOGIN_KEY] = true
        }
    }

    suspend fun setLogoutStatus() {
        context.dataStore.edit { pref ->
            pref[LOGIN_KEY] = false
            pref.clear()
        }
    }

    suspend fun setAccessToken(accessToken: String) {
        context.dataStore.edit { pref ->
            pref[ACCESS_TOKEN] = accessToken
        }
    }

    fun getStatusLogin(): Flow<Boolean> {
        return context.dataStore.data.map { pref ->
            pref[LOGIN_KEY] ?: false
        }
    }

    fun getAccessToken(): Flow<String> {
        return context.dataStore.data.map { pref ->
            pref[ACCESS_TOKEN] ?: "null"
        }
    }

    companion object {
        private const val DATASTORE_NAME = "data_store"

        private val LOGIN_KEY = booleanPreferencesKey("login_key")
        private val ACCESS_TOKEN = stringPreferencesKey("KEY_ACCESS_TOKEN")

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)
    }
}