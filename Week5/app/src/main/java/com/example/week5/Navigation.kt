package com.example.week5

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.week5.ui.screens.PhotoDetailScreenViewModel
import com.example.week5.ui.screens.PhotoDetailsScreen
import com.example.week5.ui.screens.UserScreen
import com.example.week5.ui.screens.UserScreenViewModel


sealed class Screens(val route: String) {
    object userScreen: Screens("userScreen")
    object photoScreen: Screens("photoScreen/{photoId}")
    fun createRoute(photoId: String) = "photoScreen/$photoId"
}
@Composable
fun AppNavHost() {
    val userScreenViewModel:UserScreenViewModel = viewModel()
    val detailsScreenViewModel: PhotoDetailScreenViewModel = viewModel()
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Screens.userScreen.route) {

        composable(route = Screens.userScreen.route) {
            UserScreen(viewModel = userScreenViewModel,
                navController = navController)
        }

        composable(route = Screens.photoScreen.route) { navBackStackEntry ->
            val photoId = navBackStackEntry.arguments?.getString("photoId")
            if(photoId == null) {
                Toast.makeText(context, "userId is required for navigation", Toast.LENGTH_SHORT).show()
            }else {
                PhotoDetailsScreen(photoId = photoId, navController = navController, viewModel = detailsScreenViewModel)
            }

        }

    }
}