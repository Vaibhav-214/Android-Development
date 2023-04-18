package com.example.week6.ui.screens.usersscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.week6.R
import com.example.week6.navigation.Destinations
import com.example.week6.network.User
import com.example.week6.utility.components.CustomBottomAppBar
import com.example.week6.utility.components.ReusableIconButton
import com.example.week6.utility.components.ReusableTopBar
import com.example.week6.utility.debugLog
import com.example.week6.utility.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    navController: NavHostController,
    viewModel: UsersScreenViewModel
) {

    val uiState by remember {
        viewModel.userScreenUiState
    }.collectAsState()

    Scaffold(
        topBar = {
            ReusableTopBar(title = R.string.users, iconId = R.drawable.home)
        },
    ) { pad ->

        LaunchedEffect(key1 = Unit) {
            viewModel.getUsers()
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .background(
                    color = MaterialTheme.colorScheme.primary
                )
        ) {

            UserGrid(uiState = uiState,
                modifier = Modifier,
                onCLick = { navController.navigate(Destinations.UserProfileScreen.createRoute(it)) })

            CustomBottomAppBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                containerColor = MaterialTheme.colorScheme.secondary

            ) {
                ReusableIconButton(iconId = R.drawable.user)

                ReusableIconButton(
                    iconId = R.drawable.settings,
                    onClick = { navController.navigate(Destinations.SettingsScreen.route) }
                )
            }
        }
    }
}

@Composable
fun SuccessView(
    modifier: Modifier,
    users: LazyPagingItems<User>,
    onClick: (String) -> Unit
) {
    LazyVerticalGrid(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp), columns = GridCells.Fixed(3)
    ) {

        items(users) { user ->
            user?.let { user ->
                UserBlock(user = user, onClick = { onClick(it) })
            }
        }

    }
}

@Composable
fun UserBlock(
    user: User,
    onClick: (String) -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(user.avatar)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
                .aspectRatio(1f)
                .clip(shape = CircleShape)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = CircleShape,
                )
                .clickable { onClick(user.id) },
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable._67c213c859e1904),
        )
    }
}

@Composable
fun LoadingView(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            color = Color.Black,
            modifier = Modifier.align(Alignment.Center)
            )

    }
}

@Composable
fun ErrorView(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize()) {

        Text(
            text = stringResource(id = R.string.error),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black
        )
    }
}

@Composable
fun UserGrid(
    uiState: UserScreenUiState,
    modifier: Modifier,
    onCLick: (String) -> Unit
) {
    when (uiState) {

        is UserScreenUiState.Success -> {
            debugLog("Inside when-success")
            SuccessView(
                modifier = modifier,
                users = uiState.userList.collectAsLazyPagingItems(),
                onClick = { onCLick(it) }
            )
        }

        is UserScreenUiState.Loading -> {
            debugLog("Inisde when - loading")
            LoadingView(modifier = modifier)
        }

        else -> {
            debugLog("Inside when-else-error")
            ErrorView(modifier = modifier)
        }
    }
}

