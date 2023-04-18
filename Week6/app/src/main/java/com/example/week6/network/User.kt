package com.example.week6.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
  val createdAt: String,
  val name: String,
  val avatar: String,
  val about: String?,
  val followers:Int,
  val following: Int,
  val posts: Int,
  val id: String
)
