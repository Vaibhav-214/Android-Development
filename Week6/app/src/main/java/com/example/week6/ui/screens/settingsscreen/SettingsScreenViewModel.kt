package com.example.week6.ui.screens.settingsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week6.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val language: StateFlow<String> = dataStoreManager.language.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = "en"
    )

    fun saveLanguage(languageCode: String) = viewModelScope.launch {
        dataStoreManager.saveLanguage(languageCode = languageCode)
    }
}