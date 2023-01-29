package com.example.amphibians.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.amphibians.R
import com.example.amphibians.network.Amphibian
import com.example.amphibians.ui.AmphibianUiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    amphibianUiState: AmphibianUiState,
    retry: () -> Unit
    ) {
    when(amphibianUiState) {
        is AmphibianUiState.Loading -> LoadingScreen(modifier)
        is AmphibianUiState.Success -> AmphibianListView(amphibianList = amphibianUiState.amphibians)
        else -> ErrorScreen(retry = retry)
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
        ) {
        Image(
            painter = painterResource(id = R.drawable.frog) ,
            contentDescription = null,
            contentScale = ContentScale.Crop
            )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, retry: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Can't connect to the Internet", fontSize = 30.sp)
        Button(onClick = retry)  {
            Text(text = "RETRY")
        }
    }
}

@Composable
fun AmphibianListView(amphibianList: List<Amphibian>, modifier: Modifier = Modifier) {
    LazyColumn {
        items(amphibianList,
            key = { amphibian ->
                amphibian.name }
            ) { amphibian ->
            AmphibianCard(amphibian = amphibian)
        }
    }
}

@Composable
fun AmphibianCard(modifier: Modifier = Modifier, amphibian: Amphibian) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1f),
        elevation = 8.dp,
    ) {
        Column() {
            Text(
                text ="${amphibian.name} (${amphibian.type})",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
            Text(text = amphibian.description)
            AsyncImage(
                model = amphibian.imgSrc,
                contentDescription = null,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
                )
        }
    }
}