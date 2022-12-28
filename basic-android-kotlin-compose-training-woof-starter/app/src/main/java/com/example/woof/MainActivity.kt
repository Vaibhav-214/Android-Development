/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.woof

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.woof.data.Dog
import com.example.woof.data.dogs
import com.example.woof.ui.theme.Green25
import com.example.woof.ui.theme.White
import com.example.woof.ui.theme.WoofTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WoofTheme {
                WoofApp()
            }
        }
    }
}

@Composable
fun WoofApp() {
    Scaffold(
        topBar = { WooOfTapBarApp()}
    ) {
        LazyColumn (modifier = Modifier.background(MaterialTheme.colors.background)){
            items(dogs) {
                DogItem(dog = it)
            }
        }
    }

}
/**
 * Composable that displays an app bar and a list of dogs.
 */



@Composable
fun WooOfTapBarApp(modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.primary),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Image(
            modifier = Modifier
                .size(64.dp)
                .padding(8.dp),
            painter = painterResource(R.drawable.ic_woof_logo),
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h1

        )
    }
}

/**
 * Composable that displays a list item containing a dog icon and their information.
 *
 * @param dog contains the data that populates the list item
 * @param modifier modifiers to set to this composable
 */
@Composable
fun DogItem(dog: Dog, modifier: Modifier = Modifier) {

    val color by animateColorAsState(
        targetValue = if (dog.expanded.value) Green25 else MaterialTheme.colors.surface,
    )
    // adding card composable because row doesn't have the ability to shape
    // A Card is a surface that can contain a single composable and contains options for decoration.
    // The decoration can be added through the border, shape, elevation, and more.
    // Also card is a medium size component therefore we will change it shape through Shape.kt file
    //Since a Card, by default, already uses the medium shape, you do not have to explicitly set it
    // to the medium shape. Refresh the Preview and you will see the rounded corners
    Card(modifier = modifier.padding(8.dp),
        elevation = 4.dp
        ) {
        Column(modifier = modifier.animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )).background(color = color)) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                //A Card is a Surface, and in the Theme.kt file, we have a color explicitly set
                // for the surface slot. Because of that, we can remove color from the Row and do
                // not explicitly need to set it to the surface color.
                //.background(MaterialTheme.colors.surface)
            ) {
                DogIcon(dog.imageResourceId)
                DogInformation(dog.name, dog.age, expanded = dog.expanded.value)
                Spacer(modifier = modifier.weight(1f))
                // will take all the available space in between since no other component in row has
                // defined weight
                DogItemButton(expanded = dog.expanded.value,
                    onClick = {dog.expanded.value = !dog.expanded.value})
               //  change true to false and false to true

            }
            if (dog.expanded.value) {
                DogHobby(dogHobby = dog.hobbies, expanded = dog.expanded.value) }

        }
       
    }

}

@Composable
private fun DogItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick) {
        Icon(imageVector = if (expanded) Icons.Filled.ExpandLess  else Icons.Filled.ExpandMore,
            tint = MaterialTheme.colors.secondary,
            contentDescription = stringResource(id = R.string.expand_button_content_description))
    }
}

@Composable
private fun DogHobby(@StringRes dogHobby: Int, modifier: Modifier = Modifier, expanded: Boolean) {
    Column(
        modifier = modifier.padding(
            start = 16.dp,
            top = 8.dp,
            bottom = 16.dp,
            end = 16.dp
        )
    ){
        Text(text = stringResource(id = R.string.about),
            style = MaterialTheme.typography.h3,
            color = if (expanded) White else MaterialTheme.colors.onSurface
        )
        Text(text = stringResource(id = dogHobby), style = MaterialTheme.typography.body1,
            color = if (expanded) White else MaterialTheme.colors.onSurface
        )

    }
}

/**
 * Composable that displays a photo of a dog.
 *
 * @param dogIcon is the resource ID for the image of the dog
 * @param modifier modifiers to set to this composable
 */
@Composable
fun DogIcon(@DrawableRes dogIcon: Int, modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .size(64.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(50)),// 50 means full circle
        painter = painterResource(dogIcon),
        contentScale = ContentScale.Crop,
        // To know why content scale is used comment it and see preview
        /*
         * Content Description is not needed here - image is decorative, and setting a null content
         * description allows accessibility services to skip this element during navigation.
         */
        contentDescription = null
    )
}

/**
 * Composable that displays a dog's name and age.
 *
 * @param dogName is the resource ID for the string of the dog's name
 * @param dogAge is the Int that represents the dog's age
 * @param modifier modifiers to set to this composable
 */
@Composable
fun DogInformation(@StringRes dogName: Int, dogAge: Int, modifier: Modifier = Modifier, expanded: Boolean) {
    Column {
        //Since the Text for the dog's name and age are on top of a Surface(Card), their color defaults
        // to onSurface. Remove the explicit color setting for both Text items in DogInformation().
        Text(
            text = stringResource(dogName),
            style = MaterialTheme.typography.h2,
            modifier = modifier.padding(top = 8.dp),
             color =if (expanded) White else MaterialTheme.colors.onSurface
        )
        Text(
            text = stringResource(R.string.years_old, dogAge),
            style = MaterialTheme.typography.body1,
            color = if (expanded) White else MaterialTheme.colors.onSurface

            // color = MaterialTheme.colors.onSurface
        )
    }
}

/**
 * Composable that displays what the UI of the app looks like in light theme in the design tab.
 */
@Preview
@Composable
fun WoofPreview() {
    WoofTheme(darkTheme = false) {
        WoofApp()
    }
}
@Preview
@Composable
fun DarkThemePreview() {
    WoofTheme(darkTheme = true) {
        WoofApp()
    }
}
