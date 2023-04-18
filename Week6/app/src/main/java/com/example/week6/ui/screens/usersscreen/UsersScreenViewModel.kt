package com.example.week6.ui.screens.usersscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.week6.data.UserRepository
import com.example.week6.utility.debugLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersScreenViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _userScreenUiState = MutableStateFlow<UserScreenUiState>(UserScreenUiState.Loading)
    val userScreenUiState = _userScreenUiState.asStateFlow()

    fun getUsers() {
        viewModelScope.launch {
            _userScreenUiState.value = UserScreenUiState.Loading
            try {
                debugLog("userUiState ${_userScreenUiState.value}")
                val userlist = userRepository.getUserList().cachedIn(viewModelScope)
                _userScreenUiState.value = UserScreenUiState.Success(userList = userlist)

                debugLog("userUiState after req ${_userScreenUiState.value}")
            } catch (e: Exception) {
                _userScreenUiState.value = UserScreenUiState.Error
                debugLog("exception in viewModel getUsers method")
            }
        }
    }
}