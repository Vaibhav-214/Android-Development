package com.example.week6.ui.screens.postdetailscreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.week6.R
import com.example.week6.network.Post
import com.example.week6.utility.components.ReusableAsyncImage
import com.example.week6.utility.components.ReusableIconButton
import com.example.week6.utility.components.Stats
import com.example.week6.utility.components.VerticalSpacer
import com.example.week6.utility.debugLog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    navController: NavHostController,
    viewModel: PostDetailViewModel,
    userName: String,
    postId: String,
    userId: String
) {
    LaunchedEffect(key1 = Unit) {
        debugLog("userID: $userId, postId: $postId")
        viewModel.getPostDetail(userId = userId, postId = postId)
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            viewModel.toLoadingState()
        }
    }

    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.post_detail),
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    ReusableIconButton(iconId = R.drawable.info)
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                actions = {
                    ReusableIconButton(
                        iconId = R.drawable.trash,
                        onClick = {
                            viewModel.deletePostRequest(userId = userId, postId = postId)
                            navController.popBackStack()
                            Toast.makeText(
                                context,
                                "Post will be deleted in 5 sec",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.primary
    ) {

        when (uiState) {
            is PostDetailsScreenUiState.Loading -> LoadingView(
                modifier = Modifier.padding(it)
            )

            is PostDetailsScreenUiState.Success -> SuccessView(
                modifier = Modifier.padding(it),
                userName = userName, post = uiState.post
            )

            else -> ErrorView(
                modifier = Modifier.padding(it)
            )
        }

    }

}


@Composable
fun SuccessView(
    modifier: Modifier,
    post: Post,
    userName: String
) {
    Column(
        modifier = modifier.padding(10.dp)
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(post.url)
                .memoryCachePolicy(CachePolicy.DISABLED)
                .diskCachePolicy(CachePolicy.DISABLED)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(shape = RectangleShape),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable._1289519_9ebdbe1a_aae6_11e7_8f82_bf794fdd9d1a),
            error = painterResource(id = R.drawable._67c213c859e1904),
        )

        VerticalSpacer(height = 20)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Stats(type = stringResource(id = R.string.likes), amount = post.likesCount)

            Stats(type = stringResource(id = R.string.comments), amount = post.commentsCount)

        }

        VerticalSpacer(height = 20)

        Text(text = post.caption, color = Color.Black)

        VerticalSpacer(height = 20)

        Text(text = userName, color = Color.Black)
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
            text = stringResource(R.string.loading),
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
        Text(text = stringResource(R.string.error), style = MaterialTheme.typography.headlineLarge)
    }
}
