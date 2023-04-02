package com.example.week5.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.week5.data.NetworkRepository
import com.example.week5.network.UserModel
import com.example.week5.network.photos.Photo
import com.example.week5.utility.debugLog
import com.example.week5.utility.userModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserScreenViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
):ViewModel() {

    private val _userUiState = MutableStateFlow(userModel)
    var userUiState = _userUiState.asStateFlow()

        fun getUserInfo() = viewModelScope.launch {
            try {
                _userUiState.value = networkRepository.getUser()
                debugLog("successful in network request ${_userUiState.value}")
            }catch (e: Exception) {
                e.printStackTrace()
                debugLog("exception caught in viewModel $e")
            }
        }

     var photosUiState = MutableStateFlow<PagingData<Photo>>(PagingData.empty())
        private set

    fun getPhotos() {
        viewModelScope.launch {
            networkRepository
                .getPhotoList()
                .cachedIn(viewModelScope)
                .collect {
                    photosUiState.value = it
                    debugLog("This is read/write value ${photosUiState.value}")
                    debugLog("This is read only value $photosUiState")
                }
        }
    }
}