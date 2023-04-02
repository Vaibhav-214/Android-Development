package com.example.week5.network

import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {
    @GET("api/v2/users")
    suspend fun getUserDetails(
        @Query("size") size: Int,
    ): UserModel
}