package com.example.week6.utility.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun ReusableIconButton (
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    @DrawableRes iconId: Int
) {
    IconButton(
        modifier = modifier.padding(horizontal = 10.dp),
        onClick = onClick) {
        Icon(
            painter = painterResource(id = iconId),
            tint = MaterialTheme.colorScheme.onSecondary,
            contentDescription = null
        )
    }
}