package com.example.week6.utility.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.week6.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReusableTopBar(
    @StringRes title: Int,
    @DrawableRes iconId: Int
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        },
        navigationIcon = {
            ReusableIconButton(iconId = iconId)
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    )
}