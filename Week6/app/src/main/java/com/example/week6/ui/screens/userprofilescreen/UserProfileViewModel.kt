package com.example.week6.ui.screens.userprofilescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week6.data.UserRepository
import com.example.week6.network.Post
import com.example.week6.utility.debugLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserProfileUiState>(UserProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _postList = MutableStateFlow<List<Post>>(listOf())
    val postList = _postList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun getUser(userId: String) {
        viewModelScope.launch {
            try {
                _uiState.value = UserProfileUiState.Success(
                    userRepository.getUser(userId = userId)
                )
            } catch (e: Exception) {
                debugLog("exception caught in userProfile VM in getUser method e: $e")
                _uiState.value = UserProfileUiState.Error
            }
        }
    }

    fun getPosts(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _postList.value = userRepository.getUserPosts(userId = userId)

            _isLoading.value = false
            debugLog("Inside UserProfile ViewModel getPosts method after fetch")
        }
    }

    fun toLoadingState() {
        _uiState.value = UserProfileUiState.Loading
    }
}