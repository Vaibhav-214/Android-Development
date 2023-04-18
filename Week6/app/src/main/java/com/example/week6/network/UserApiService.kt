package com.example.week6.network

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface UserApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("limit") size: Int,
    ): List<User>

    @GET("users/{userId}/Post")
    suspend fun getPosts(
        @Path("userId") userId: String,
    ): List<Post>

    @GET("users/{userId}")
    suspend fun getUser(
        @Path("userId") userId: String
    ): User

    @GET("users/{userId}/Post/{postId}")
    suspend fun getPostDetail(
        @Path("userId") userId: String,
        @Path("postId") postId: String,
        //@Query("dummy") dummy: Int
    ): Post

    @POST("users/{userId}/Post")
    suspend fun createPost(
        @Path("userId") userId: String
    ): Response<Post>

    @DELETE("users/{userId}/Post/{postId}")
    suspend fun deletePost(
        @Path("userId") userId: String,
        @Path("postId") postId: String
    ): Response<Unit>
}