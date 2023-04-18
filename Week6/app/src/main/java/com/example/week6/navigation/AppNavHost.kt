package com.example.week6.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.week6.ui.screens.addpostscreen.AddPostScreen
import com.example.week6.ui.screens.addpostscreen.AddPostViewModel
import com.example.week6.ui.screens.postdetailscreen.PostDetailScreen
import com.example.week6.ui.screens.postdetailscreen.PostDetailViewModel
import com.example.week6.ui.screens.settingsscreen.SettingsScreen
import com.example.week6.ui.screens.settingsscreen.SettingsScreenViewModel
import com.example.week6.ui.screens.userprofilescreen.UserProfileScreen
import com.example.week6.ui.screens.userprofilescreen.UserProfileViewModel
import com.example.week6.ui.screens.usersscreen.UserScreen
import com.example.week6.ui.screens.usersscreen.UsersScreenViewModel

@Composable
fun AppNavHost(settingViewModel: SettingsScreenViewModel) {
    val navController: NavHostController = rememberNavController()
    val userScreenViewModel: UsersScreenViewModel = hiltViewModel()
    val userProfileViewModel: UserProfileViewModel = hiltViewModel()
    val postDetailViewModel: PostDetailViewModel = hiltViewModel()
    val addPostViewModel: AddPostViewModel = hiltViewModel()
    val context = LocalContext.current


    NavHost(navController = navController, startDestination = Destinations.UserScreen.route) {
        composable(Destinations.UserScreen.route) {
            UserScreen(viewModel = userScreenViewModel, navController = navController)
        }

        composable(route = Destinations.UserProfileScreen.route) { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getString("userId")
            if (userId == null) {
                Toast.makeText(context, "userId is required for navigation", Toast.LENGTH_SHORT)
                    .show()
            } else {
                UserProfileScreen(
                    viewModel = userProfileViewModel,
                    userId = userId,
                    navController = navController
                )
            }
        }
        composable(route = Destinations.PostDetailScreen.route) { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getString("userId")
            val postId = navBackStackEntry.arguments?.getString("postId")
            val userName = navBackStackEntry.arguments?.getString("userName")
            if (userId == null || userName == null || postId == null) {
                Toast.makeText(context, "userId is required for navigation", Toast.LENGTH_SHORT)
                    .show()
            } else {
                PostDetailScreen(
                    viewModel = postDetailViewModel,
                    userId = userId,
                    userName = userName,
                    postId = postId,
                    navController = navController
                )
            }
        }

        composable(route = Destinations.AddPostScreen.route) { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getString("userId")
            if (userId == null) {
                Toast.makeText(context, "userId is required for navigation", Toast.LENGTH_SHORT)
                    .show()
            } else {
                AddPostScreen(
                    viewModel = addPostViewModel,
                    userId = userId,
                    navController = navController
                )
            }
        }

        composable(route = Destinations.SettingsScreen.route) {
            SettingsScreen(navController = navController, viewModel = settingViewModel)
        }
    }
}