package com.example.week5.network.photos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photo(
    val author: String,
    @Json(name = "download_url")
    val downloadUrl: String,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)

