package com.example.week6.ui.screens.userprofilescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.week6.R
import com.example.week6.navigation.Destinations
import com.example.week6.network.Post
import com.example.week6.network.User
import com.example.week6.utility.components.HorizontalSpacer
import com.example.week6.utility.components.ReusableTopBar
import com.example.week6.utility.components.Stats
import com.example.week6.utility.components.VerticalSpacer
import com.example.week6.utility.debugLog
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    navController: NavHostController,
    viewModel: UserProfileViewModel,
    userId: String,
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getPosts(userId = userId)
        viewModel.getUser(userId = userId)
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            viewModel.toLoadingState()
        }
    }

    val uiState = viewModel.uiState.collectAsState().value
    val postList by remember { viewModel.postList }.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    Scaffold(
        topBar = {
            ReusableTopBar(title = R.string.user_profile, iconId = R.drawable.user)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Destinations.AddPostScreen.createRoute(userId = userId)) },
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    tint = MaterialTheme.colorScheme.onSecondary,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    ) { pad ->

        when (uiState) {
            is UserProfileUiState.Loading -> LoadingView(modifier = Modifier.padding(pad))
            is UserProfileUiState.Success -> SuccessView(modifier = Modifier.padding(pad),
                user = uiState.user,
                posts = postList,
                onClick = {
                    debugLog("userId: ${uiState.user.id} postId: $it")
                    navController.navigate(
                        Destinations.PostDetailScreen.createRoute(
                            userName = uiState.user.name,
                            userId = userId,
                            postId = it
                        )
                    )
                },
                swipeRef = swipeRefreshState,
                onRefresh = { viewModel.getPosts(userId = userId) }
            )

            else -> ErrorView(modifier = Modifier.padding(pad))
        }
    }
}

@Composable
fun UserInfo(
    user: User
) {
    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shadowElevation = 10.dp,
                shape = CircleShape
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(user.avatar)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .aspectRatio(1f)
                        .clip(shape = CircleShape)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = CircleShape
                        ),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable._1289519_9ebdbe1a_aae6_11e7_8f82_bf794fdd9d1a),
                    error = painterResource(id = R.drawable._67c213c859e1904)
                )
            }

            HorizontalSpacer(width = 10)

            Stats(type = stringResource(R.string.posts), amount = user.posts)

            HorizontalSpacer(width = 10)

            Stats(type = stringResource(R.string.followers), amount = user.followers)

            HorizontalSpacer(width = 10)

            Stats(type = stringResource(R.string.following), amount = user.following)
        }
        VerticalSpacer(height = 20)
        Text(
            text = user.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
        )
        VerticalSpacer(height = 5)
        Text(
            text = user.about ?: "",
            style = MaterialTheme.typography.bodyLarge
        )

        VerticalSpacer(height = 10)

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
fun SuccessView(
    modifier: Modifier,
    user: User,
    posts: List<Post>,
    onClick: (String) -> Unit,
    swipeRef: SwipeRefreshState,
    onRefresh: () -> Unit
) {
    SwipeRefresh(
        state = swipeRef,
        onRefresh = onRefresh
    ) {
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.primary
                )
                .padding(10.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(35.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                UserInfo(user = user)
            }

            items(posts) { post ->
                UserPost(post = post, onClick = { onClick(it) })
            }
        }

    }
}

@Composable
fun LoadingView(
    modifier: Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.loading),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun ErrorView(
    modifier: Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.error),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun UserPost(
    post: Post,
    onClick: (String) -> Unit
) {
    AsyncImage(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(post.url)
            .build(),
        contentDescription = null,
        modifier = Modifier
            .size(150.dp)
            .padding(8.dp)
            .aspectRatio(1f)
            .clip(shape = RectangleShape)
            .clickable {
                onClick(post.id)
            },
        contentScale = ContentScale.Crop,
        error = painterResource(id = R.drawable._67c213c859e1904)
    )
}
