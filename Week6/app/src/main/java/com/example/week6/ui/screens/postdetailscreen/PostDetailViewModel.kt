package com.example.week6.ui.screens.postdetailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.week6.data.UserRepository
import com.example.week6.work.DeletePostWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val workManager: WorkManager
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<PostDetailsScreenUiState>(PostDetailsScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getPostDetail(userId: String, postId: String) {
        viewModelScope.launch {
            _uiState.value = PostDetailsScreenUiState
                .Success(userRepository.getPostDetail(userId = userId, postId = postId))
        }
    }

    fun deletePostRequest(userId: String, postId: String) {
        val data = Data.Builder()
            .putString("userId", userId)
            .putString("postId", postId)
            .build()
        val workRequest = OneTimeWorkRequest.Builder(DeletePostWorker::class.java)
            .setInputData(data)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()
        workManager.enqueue(workRequest)
    }

    fun toLoadingState() {
        _uiState.value = PostDetailsScreenUiState.Loading
    }

}

