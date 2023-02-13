package com.example.bookshelf.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.example.bookshelf.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun BookShelfApp(modifier: Modifier = Modifier, bookShelfViewModel: BookShelfViewModel) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(stringResource(R.string.app_name)) }) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {
            HomeScreen(
                bookShelfUiState = bookShelfViewModel.bookShelfUiState,
                retry= bookShelfViewModel::getBooks
            )
        }
    }
}