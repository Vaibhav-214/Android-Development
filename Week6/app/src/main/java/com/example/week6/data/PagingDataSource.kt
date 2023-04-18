package com.example.week6.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.week6.network.User
import com.example.week6.network.UserApiService
import com.example.week6.utility.debugLog
import retrofit2.HttpException


class PagingDataSource(
    private val userApiService: UserApiService
) : PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val currentPage = params.key ?: 1
            val response = userApiService
                .getUsers(currentPage, params.loadSize)

            LoadResult.Page(
                data = response,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.isEmpty()) null else currentPage + 1
            )
        } catch (httpE: HttpException) {
            debugLog("httpE exception : $httpE")
            // throw httpE
            LoadResult.Error(httpE)

        } catch (e: Exception) {

            debugLog("exception in load function : $e")
            //throw e
            LoadResult.Error(e)
        }
    }
}