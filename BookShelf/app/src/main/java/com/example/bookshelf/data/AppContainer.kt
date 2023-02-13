package com.example.bookshelf.data

import com.example.bookshelf.network.BookApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.moshi.Moshi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit
import retrofit2.create

interface AppContainer {
    val bookShelfRepository: BookShelfRepository
}

class OnlineAppContainer: AppContainer {
    private val BASE_URL ="https://www.googleapis.com/books/v1/"


    @ExperimentalSerializationApi
    private val retrofit = Retrofit.Builder()
        // .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        // problem of deserialization was due to this
            // because I m not defining all the keys from json response in my data class
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()


    @OptIn(ExperimentalSerializationApi::class)
    private val retrofitService : BookApiService by lazy {
        retrofit.create(BookApiService::class.java)
    }

    override val bookShelfRepository: BookShelfRepository by lazy {
        OnlineBooksRepository(retrofitService)
    }


}

