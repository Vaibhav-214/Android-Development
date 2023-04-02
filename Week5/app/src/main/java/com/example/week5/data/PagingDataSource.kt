package com.example.week5.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.week5.network.photos.Photo
import com.example.week5.network.photos.PhotoApiService
import retrofit2.HttpException

class PagingDataSource(
    private val photoApiService: PhotoApiService
):PagingSource<Int,Photo>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val currentPage = params.key ?: 1
            val response = photoApiService
                .getPhotos(currentPage, params.loadSize)

            LoadResult.Page(
                data = response,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }catch (httpE: HttpException) {
            LoadResult.Error(httpE)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}