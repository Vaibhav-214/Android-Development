package com.example.week5.network.photos

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoApiService {
    @GET("v2/list")
    fun getPhotos(
        @Query("page") page: Int,
        @Query("limit") size: Int,
    ): List<Photo>

    @GET("id/{id}/info")
    fun getPhoto(
        @Path("id") id: String
    ):Photo
}