package com.example.week6.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.week6.network.Post
import com.example.week6.network.User
import com.example.week6.network.UserApiService
import com.example.week6.utility.debugLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApiService: UserApiService
) {

    fun getUserList(): Flow<PagingData<User>> {
        debugLog("repository getUserList function getting executed")

        return Pager(
            config = PagingConfig(pageSize = 30, initialLoadSize = 30)
        ) {
            PagingDataSource(userApiService = userApiService)
        }.flow.catch { e ->
            debugLog("exception caught and thrown from repo try block")
            throw e
        }
    }

    suspend fun getUserPosts(userId: String): List<Post> {
        return userApiService.getPosts(userId = userId)
    }

    suspend fun getUser(userId: String): User {
        return userApiService.getUser(userId = userId)
    }

    suspend fun getPostDetail(userId: String, postId: String): Post {
        return userApiService.getPostDetail(
            userId = userId,
            postId = postId,
        )
    }

    suspend fun createPost(post: Post): Post? {
        val response = userApiService.createPost(post.userId)
        if (!response.isSuccessful) return null
        return response.body()
    }

    suspend fun deletePost(userId: String, postId: String) {
        try {
            userApiService.deletePost(
                userId = userId,
                postId = postId
            )
        } catch (e: Exception) {
            debugLog("exception repo deletepost: $e")
            throw e
        }
    }
}