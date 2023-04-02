package com.example.week5.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.week5.network.UserApiService
import com.example.week5.network.UserModel
import com.example.week5.network.photos.Photo
import com.example.week5.network.photos.PhotoApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(
    private val userApiService: UserApiService,
    private val photoApiService: PhotoApiService
) {

    // trying to work without flow since the data is not gonna change for this request in middle
    suspend fun getUser():UserModel {
        return userApiService.getUserDetails(1)
    }

    fun getPhotoList(): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(pageSize = 20, initialLoadSize = 15)
        ) {
            PagingDataSource(photoApiService = photoApiService)
        }.flow

    fun getPhoto(id: String): Photo {
        return photoApiService.getPhoto(id)
    }
}