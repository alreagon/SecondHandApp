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
// apa lah
    companion object {
        private const val DATASTORE_NAME = "data_store"

        private val LOGIN_KEY = booleanPreferencesKey("login_key")
        private val USERID_KEY = intPreferencesKey("user_id")

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

        private val EMAIL = stringPreferencesKey("KEY_EMAIL")
        private val PASSWORD = stringPreferencesKey("KEY_PASSWORD")
        private val ACCESS_TOKEN = stringPreferencesKey("KEY_ACCESS_TOKEN")
    }

    suspend fun loginUserData(
        email : String,
        password : String,
        access_token : String
    )
    {
        context.dataStore.edit {
            it[EMAIL] = email
            it[PASSWORD] = password
            it[ACCESS_TOKEN] = access_token
        }
    }

    val email : Flow<String> = context.dataStore.data.map {
        it[EMAIL] ?: ""
    }

    val password : Flow<String> = context.dataStore.data.map {
        it[PASSWORD] ?: ""
    }

    val access_token : Flow<String> = context.dataStore.data.map {
        it[ACCESS_TOKEN] ?: ""
    }

    suspend fun logoutUserData(){
        GlobalScope.launch {
            context.dataStore.edit {
                it.clear()
            }
        }
    }
}