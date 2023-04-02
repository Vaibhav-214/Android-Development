package com.example.week5.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.week5.data.NetworkRepository
import com.example.week5.network.photos.Photo
import com.example.week5.utility.debugLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PhotoDetailScreenViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
):ViewModel() {


    private val _currentPhotoUiState = MutableStateFlow<Photo>(
        Photo("Vaibhav", "", 23, "", "", 23
    ))
    val photoUiState = _currentPhotoUiState

    fun updateCurrentPhoto(id: String) {
        _currentPhotoUiState.value = networkRepository.getPhoto(id)
    }
}