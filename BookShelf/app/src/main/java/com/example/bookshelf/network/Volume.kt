package com.example.bookshelf.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookResponse(
    val items: List<Book>?,
)

@Serializable
data class Book (
    val id: String,
    val volumeInfo: VolumeInfo,
    val etag: String
    )

@Serializable
data class VolumeInfo (
    val title: String,
    val imageLinks: ImageLinks ?= null
)

@Serializable
data class ImageLinks (
    val thumbnail: String
)
