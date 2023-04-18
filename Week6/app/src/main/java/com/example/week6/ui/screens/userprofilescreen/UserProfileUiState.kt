package com.example.week6.ui.screens.userprofilescreen

import com.example.week6.network.User

sealed interface UserProfileUiState {
    data class Success(val user: User) : UserProfileUiState

    object Error : UserProfileUiState

    object Loading : UserProfileUiState
}
