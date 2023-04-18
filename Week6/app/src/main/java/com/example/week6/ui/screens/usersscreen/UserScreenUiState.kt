package com.example.week6.ui.screens.usersscreen

import androidx.paging.PagingData
import com.example.week6.network.User
import kotlinx.coroutines.flow.Flow

sealed interface UserScreenUiState {
    data class Success(val userList: Flow<PagingData<User>>) : UserScreenUiState

        object Error : UserScreenUiState

        object Loading : UserScreenUiState
}
