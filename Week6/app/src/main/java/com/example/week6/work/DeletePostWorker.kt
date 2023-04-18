package com.example.week6.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.week6.data.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope

@HiltWorker
class DeletePostWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val userRepository: UserRepository
): CoroutineWorker(context,workerParameters) {

    override suspend fun doWork(): Result {

        val userId = inputData.getString("userId")
        val postId = inputData.getString("postId")

        return coroutineScope {
            try {
                if (userId != null && postId != null) {
                    userRepository.deletePost(userId = userId, postId = postId)
                }
                Result.success()
            } catch (ex: Exception) {
                Result.failure()
            }
        }
    }
}
