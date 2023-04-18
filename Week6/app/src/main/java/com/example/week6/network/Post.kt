package com.example.week6.network

import com.squareup.moshi.JsonClass

// we are also getting same response on using POST method
@JsonClass(generateAdapter = true)
data class Post(
    val caption: String,
    val commentsCount: Int,
    val createdAt: String,
    val id: String,
    val likesCount: Int,
    val url: String,
    val userId: String
)

