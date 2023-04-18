package com.example.week6.utility.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Stats(
    type: String,
    amount: Int
) {
   Surface(
       modifier = Modifier.size(width = 82.dp, height = 82.dp),
       shape = RoundedCornerShape(15.dp),
       color = MaterialTheme.colorScheme.tertiary,
       shadowElevation = 10.dp
   ) {
       Column(
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           Text(
               text = amount.toString(),
               style = MaterialTheme.typography.headlineSmall,
               color = MaterialTheme.colorScheme.onSecondary
           )

           VerticalSpacer(height = 10)

           Text(
               text = type,
               style = MaterialTheme.typography.bodyLarge,
               color = MaterialTheme.colorScheme.onSecondary
           )
       }
   }
}