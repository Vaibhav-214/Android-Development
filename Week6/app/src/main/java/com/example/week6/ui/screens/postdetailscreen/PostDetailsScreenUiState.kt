package com.example.week6.ui.screens.postdetailscreen

import com.example.week6.network.Post

sealed interface PostDetailsScreenUiState {
    data class Success(val post: Post): PostDetailsScreenUiState
    object Loading: PostDetailsScreenUiState
    object Error: PostDetailsScreenUiState
}
