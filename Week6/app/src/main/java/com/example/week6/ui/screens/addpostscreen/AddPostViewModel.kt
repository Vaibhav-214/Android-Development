package com.example.week6.ui.screens.addpostscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week6.data.UserRepository
import com.example.week6.network.Post
import com.example.week6.utility.debugLog
import com.example.week6.utility.others.getCurrentTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _state = MutableStateFlow(AddPostScreenStates())
    val state = _state.asStateFlow()


    fun onUrlChange (newValue: String) {
        _state.value = _state.value.copy(
            url = newValue
        )
    }

    fun onCaptionChange (newValue: String) {
        _state.value = _state.value.copy(
            caption = newValue
        )
    }

    fun onLikesChange (newValue: String) {
        _state.value = _state.value.copy(
            likes = newValue
        )
    }

    fun onCommentsChange (newValue: String) {
        _state.value = _state.value.copy(
            comments = newValue
        )
    }

    fun checkAndCreatePost(userId: String): String {
        
        if (!state.value.url.isNotEmpty()) {
            return "url can't be empty"
        }
        if (!state.value.caption.isNotEmpty()) {
            return "caption can't be empty"
        }
        if (!state.value.likes.isNotEmpty()) {
            _state.value = _state.value.copy(likes = "0")
        }
        if (!state.value.comments.isNotEmpty()) {
            _state.value = _state.value.copy(comments = "0")
        }
        return try {
            createPost(userId = userId)
            _state.value = _state.value.copy(
                url = "",
                caption = "",
                likes = "",
                comments = ""
            )
            "Post Successful"
        }catch (e: Exception) {
            "Post failed"
        }

    }

    private fun createPost(userId: String) = viewModelScope.launch {
        try {
            val post = Post(
                caption = state.value.caption,
                url = state.value.url,
                userId = userId,
                createdAt = getCurrentTimestamp(),
                id = (1..100).random().toString(),
                commentsCount = state.value.comments.toInt(),
                likesCount = state.value.likes.toInt()
            )
            userRepository.createPost(post = post)
        } catch (e: Exception) {
            debugLog("exception in add post VM: $e")
            throw e
        }
    }


}