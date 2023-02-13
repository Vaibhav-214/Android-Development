package com.example.bookshelf.data

import com.example.bookshelf.network.Book
import com.example.bookshelf.network.BookApiService
import com.example.bookshelf.network.BookResponse
import retrofit2.Call
import retrofit2.Response

interface BookShelfRepository {
    suspend fun getBooksList(): Response<BookResponse>
    suspend fun getBook(id: String): Response<Book>
}


class OnlineBooksRepository (
    val bookApiService: BookApiService
): BookShelfRepository {
    override suspend fun getBooksList(): Response<BookResponse> {
            return bookApiService.getBookList()
    }
// make a parameter here to pass in the function
    override suspend fun getBook(id: String): Response<Book> {
        return bookApiService.getBookInfo(id)
    }
}