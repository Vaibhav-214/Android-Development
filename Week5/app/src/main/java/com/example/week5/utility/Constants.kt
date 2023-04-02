package com.example.week5.utility

import android.util.Log

object Constants {
    const val USER_RETRIEVEL_API = "https://random-data-api.com/"
    const val PHOTO_API = "https://picsum.photos/"
}

fun debugLog(msg: String) {
    Log.d("DEBUG", msg)
}

