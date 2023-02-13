package com.example.bookshelf.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookShelfApplication
import com.example.bookshelf.data.BookShelfRepository
import com.example.bookshelf.network.Book
import com.example.bookshelf.network.BookResponse
import com.example.bookshelf.network.ImageLinks
import kotlinx.coroutines.launch
import retrofit2.Call
import java.io.IOException

sealed interface BookShelfUiState {
    data class Success(val imageList: MutableList<String>): BookShelfUiState
    object Error : BookShelfUiState
    object Loading : BookShelfUiState
}

class BookShelfViewModel(val bookShelfRepository: BookShelfRepository) : ViewModel() {

    var bookShelfUiState: BookShelfUiState by mutableStateOf(BookShelfUiState.Loading)
        private set

    init {
        getBooks()
    }


    fun getBooks() {
        viewModelScope.launch {
            try {
                val response = bookShelfRepository.getBooksList()
               // val response = call.execute()
                val imageLinks = mutableListOf<String>()

                if (response.isSuccessful) {
                    Log.d("VM", "books request is successful")
                    val bookList = response.body()
                    Log.d("API", bookList.toString())
                    val booksId = mutableListOf<String>()
                    bookList?.items?.forEach {
                        book: Book ->
                        booksId.add(book.id)
                    }
                    Log.d("API", booksId.toString())
                    Log.d("VM", "All books id is added to the booksId mutableList")
                    booksId.forEach {
                        val responseBook = bookShelfRepository.getBook(it)
                      //  val responseBook = call.execute()
                        if (responseBook.isSuccessful) {
                            val bookResponse = responseBook.body()
                            if (bookResponse != null) {
                                Log.d("API", bookResponse.toString())
                                if (bookResponse.volumeInfo == null) {
                                    Log.d("NOBOOK","bookInfo is null")
                                }
                                val thumbnail = bookResponse.volumeInfo?.imageLinks?.thumbnail
                                if (thumbnail != null) {
                                    imageLinks.add(thumbnail)
                                } else {
                                    Log.d("IMG", "image url not found")
                                    imageLinks.add("https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api")
                                }
//                                imageLinks.add(imageLinksResponse.bookInfo.imageLinks?.thumbnail ?:
//                                "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api")
                           }else {
                                throw IOException("Didn't found imageLink")
                            }

                        }
                    }

                    bookShelfUiState = BookShelfUiState.Success(imageLinks)
                }else {
                    bookShelfUiState = BookShelfUiState.Error
                }
            } catch (e: IOException) {
                bookShelfUiState = BookShelfUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookShelfApplication)
                val bookShelfRepository = application.container.bookShelfRepository
                BookShelfViewModel(bookShelfRepository = bookShelfRepository)
            }
        }
    }

}