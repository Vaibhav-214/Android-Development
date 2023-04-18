package com.example.week6.ui.screens.addpostscreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.week6.R
import com.example.week6.utility.components.ReusableTextField
import com.example.week6.utility.components.ReusableTopBar
import com.example.week6.utility.components.VerticalSpacer


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostScreen(
    navController: NavHostController,
    viewModel: AddPostViewModel,
    userId: String
) {

    val context = LocalContext.current

    val state by remember {
        viewModel.state
    }.collectAsState()


    Scaffold(
        topBar = {
           ReusableTopBar(title = R.string.add_post, iconId = R.drawable.arrow_small_left)
        },
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary
                )
                .padding(it)
                .fillMaxSize()
                .padding(10.dp),
        ) {
            VerticalSpacer(height = 20)

            ReusableTextField(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(id = R.string.url),
                value = state.url,
                onValueChange = {viewModel.onUrlChange(it)})

            ReusableTextField(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(id = R.string.caption),
                value = state.caption,
                onValueChange = {viewModel.onCaptionChange(it)})


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)

            ) {

                ReusableTextField(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    label = stringResource(id = R.string.likes),
                    value = state.likes,
                    onValueChange = {viewModel.onLikesChange(it)})

                ReusableTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(id = R.string.comments),
                    value = state.comments,
                    onValueChange = {viewModel.onCommentsChange(it)})
            }

            VerticalSpacer(height = 20)

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val response = viewModel.checkAndCreatePost(userId = userId)
                    if (response == "Post Successful") {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                )
            ) {
                Text(
                    text = stringResource(R.string.create_post),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }

        }
    }

}