package com.example.week6.utility.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.week6.R


// didn't used this
@Composable
fun ReusableAsyncImage (
    modifier: Modifier = Modifier,
    url: String,
    context: Context
) {
    AsyncImage(
        model = ImageRequest
            .Builder(context)
            .data(url)
            .build(),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable._1289519_9ebdbe1a_aae6_11e7_8f82_bf794fdd9d1a),
        error = painterResource(id = R.drawable._67c213c859e1904),
    )
}