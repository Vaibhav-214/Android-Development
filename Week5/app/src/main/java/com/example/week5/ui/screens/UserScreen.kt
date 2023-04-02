package com.example.week5.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.paging.compose.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.week5.R
import com.example.week5.Screens
import com.example.week5.network.UserModel
import com.example.week5.network.photos.Photo
import com.example.week5.ui.theme.Week5Theme
import com.example.week5.utility.debugLog
import com.example.week5.utility.userModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    navController: NavHostController,
    viewModel: UserScreenViewModel,
) {
    viewModel.getUserInfo()
    viewModel.getPhotos()
    val uiState = remember {
        viewModel.userUiState
    }.collectAsState()

    val photoUiState = remember {
        viewModel.photosUiState
    }.collectAsLazyPagingItems()

    debugLog("this is photoUiState $photoUiState")


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.value.username,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Home, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary            )
            )
        }
    ) {pad ->
        LazyVerticalGrid(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp)
            ,columns = GridCells.Fixed(3)) {
            item (
                span = { GridItemSpan(3) }
            ){
                UserInfo(user = uiState.value)
            }


            item(
                span = {GridItemSpan(3)}
            ) {
                Address(user = uiState.value)
            }


            item (
                span = { GridItemSpan(3) }
            ){
                Subscription(user = uiState.value)
            }

            debugLog("into lazyVerticalGrid block ")

            if (photoUiState != null) {
                debugLog("photoUiState is not null")
            }else {
                debugLog("photoUiState is null")
            }

            items(photoUiState) {
                it?.let {
                    PhotoDisplay(photo = it,
                        onClick = {
                            navController.navigate(Screens.photoScreen.createRoute(it))
                        })
                }
            }

        }
    }

}




@Composable
fun UserInfo(
    user: UserModel,
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 15.dp)
    ){
        AsyncImage(
            model = ImageRequest
            .Builder(LocalContext.current)
            .data(user.avatar)
            .build()
            , contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
                .aspectRatio(1f)
                .clip(shape = CircleShape)
            ,
            contentScale = ContentScale.Crop,
            //error = painterResource(id = R.drawable.github_mark__1_)
        )
        Spacer(modifier = Modifier.width(20.dp))

        Column(
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "${user.firstName} ${user.lastName}",
                style = MaterialTheme.typography.headlineLarge
                )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = user.employment.title,
                style = MaterialTheme.typography.headlineSmall
                )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyMedium
                )

        }
}

}

@Composable
fun Address(
    user: UserModel
) {
    debugLog("this is from address composable")

    Row (
        modifier = Modifier
            .border(
                width = 2.dp,
                color = Color.DarkGray,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(20.dp)
            .fillMaxWidth()
            ,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = user.address.streetName, style = MaterialTheme.typography.bodyLarge)
            Text(text = user.address.city, style = MaterialTheme.typography.bodyLarge)
            Text(text = user.address.country, style = MaterialTheme.typography.bodyLarge)
        }

        Column (
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Text(text = user.address.streetAddress, style = MaterialTheme.typography.bodyLarge)
            Text(text = user.address.state, style = MaterialTheme.typography.bodyLarge)
            Text(text = user.address.zipCode, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun Subscription(
    user: UserModel
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),

        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = user.subscription.plan,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = user.subscription.status,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 4.dp,
            color = Color.Black
        )
    }

}

@Composable
fun PhotoDisplay(
    photo: Photo,
    onClick: (String)-> Unit
){
    debugLog(photo.downloadUrl + "this is the downloadURL")
    AsyncImage(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(photo.downloadUrl)
            .build()
        , contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .padding(8.dp)
            .aspectRatio(1f)
            .clickable {
                onClick(photo.id)
            },
        contentScale = ContentScale.Crop,
        //error = painterResource(id = R.drawable.github_mark__1_)
    )

}

inline fun <T : Any> LazyGridScope.items(
    items: LazyPagingItems<T>,
    noinline key: ((item: T) -> Any)? = null,
    noinline span: (LazyGridItemSpanScope.(item: T?) -> GridItemSpan)? = null,
    noinline contentType: (item: T?) -> Any? = { null },
    crossinline itemContent: @Composable LazyGridItemScope.(item: T?) -> Unit
) = items(
    count = items.itemCount,
    key = if (key != null) { index: Int -> items[index]?.let(key) ?: index } else null,
    span = if (span != null) {
        { span(items[it]) }
    } else null,
    contentType = { index: Int -> contentType(items[index]) }
) {
    itemContent(items[it])
}


@Preview(showBackground = true)
@Composable
fun UserScreenPreview(){
    Week5Theme {
        Subscription(user = userModel)
    }
}




