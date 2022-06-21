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

        private val LOGIN_STATUS_KEY = booleanPreferencesKey("login_status_key")
        private val ID_KEY = intPreferencesKey("username_key")
    }

    suspend fun loginUserData(
        email: String,
        password: String,
        access_token: String
    ) {
        context.dataStore.edit {
            it[EMAIL] = email
            it[PASSWORD] = password
            it[ACCESS_TOKEN] = access_token
        }
    }

    val email: Flow<String> = context.dataStore.data.map {
        it[EMAIL] ?: ""
    }

    val password: Flow<String> = context.dataStore.data.map {
        it[PASSWORD] ?: ""
    }

    val access_token: Flow<String> = context.dataStore.data.map {
        it[ACCESS_TOKEN] ?: ""
    }


    suspend fun saveUser(id: Int, status: Boolean) {
        context.dataStore.edit {
            it[ID_KEY] = id
            it[LOGIN_STATUS_KEY] = status
        }
    }

    fun getId(): Flow<Int> {
        return context.dataStore.data.map {
            it[ID_KEY] ?: 0
        }
    }

    fun getLoginStatus(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[LOGIN_STATUS_KEY] ?: false
        }
    }

    suspend fun logoutUserData() {
        GlobalScope.launch {
            context.dataStore.edit {
                it.clear()
            }
        }
    }
}