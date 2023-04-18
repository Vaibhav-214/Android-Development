package com.example.week6.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "my_datastore")

class DataStoreManager @Inject constructor(
    @ApplicationContext val context: Context
) {
    private val LANGUAGE_KEY = stringPreferencesKey(name = "language")

    val language = context.dataStore.data
        .map { preference ->
            preference[LANGUAGE_KEY] ?: "en"
        }

    suspend fun saveLanguage(languageCode: String) {
        context.dataStore.edit { preference ->
            preference[LANGUAGE_KEY] = languageCode
        }
    }
}