package com.example.week6.di

import com.example.week6.network.UserApiService
import com.example.week6.utility.BaseUrl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    //@Named("user")
    fun provideRetrofitFOrPaging(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseUrl.USERS_API)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    fun provideUsersApi(retrofit: Retrofit): UserApiService =
        retrofit.create(UserApiService::class.java)

//    @Provides
//    @Named("photo")
//    fun provideRetrofitForPhoto(
//        okHttpClient: OkHttpClient,
//        moshi: Moshi
//    ): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(Constants.PHOTO_API)
//            .client(okHttpClient)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .build()
//    }

//    @Provides
//    fun providePhotoApi(@Named("photo")retrofit: Retrofit): PhotoApiService =
//        retrofit.create(PhotoApiService::class.java)
}