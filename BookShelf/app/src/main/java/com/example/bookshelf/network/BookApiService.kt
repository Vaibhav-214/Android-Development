package com.example.bookshelf.network


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApiService {
    @GET("volumes?q=cicero")
    suspend fun getBookList(): Response<BookResponse>

    @GET("volumes/{id}")
    suspend fun getBookInfo(@Path("id")id: String): Response<Book>

}