package com.example.bookshelf.ui.screens

import android.graphics.Paint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.network.Book

@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               bookShelfUiState: BookShelfUiState,
               retry: () -> Unit,
    ) {
    when(bookShelfUiState) {
       is BookShelfUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
       is  BookShelfUiState.Success -> BookScreen(modifier = modifier.fillMaxSize(), imageLinks = bookShelfUiState.imageList  )
        else -> ErrorScreen(modifier = modifier.fillMaxSize(), retry)
    }
}


@Composable
fun BookScreen(modifier: Modifier,
               imageLinks: MutableList<String>
               ) {
LazyVerticalGrid(columns = GridCells.Fixed(3),
    modifier = modifier.fillMaxWidth(),
    contentPadding = PaddingValues(4.dp)
    ) {
    items(imageLinks) {
        item -> BookCard(imageUrl = item)
    }
}
}

@Composable
fun LoadingScreen(modifier: Modifier ) {
    Text(text = "Your books are on the way",
        fontSize = 20.sp,
        textAlign = TextAlign.Center
        )
}
@Composable
fun ErrorScreen(modifier: Modifier, retry: () -> Unit) {
    Text(text = "Please check your internet",
        fontSize = 20.sp
        )
    Button(onClick = retry) {
        Text(text = "RETRY",
            fontSize =30.sp)
    }
}
@Composable
fun BookCard(modifier: Modifier = Modifier,
    imageUrl: String
) {
    Card(modifier = modifier
        .padding(4.dp)
        .fillMaxWidth()
        .aspectRatio(1f),
        elevation = 8.dp,) {

        AsyncImage(model = ImageRequest.Builder(context = LocalContext.current)
            .data(imageUrl.replace("http", "https"))
            .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            error = painterResource(id = R.drawable.ic_connection_error),
            placeholder = painterResource(id = R.drawable.loading_img)
            )

    }

}