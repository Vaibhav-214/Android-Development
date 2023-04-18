package com.example.week6.navigation

sealed class Destinations(val route: String) {
    object UserScreen : Destinations("userScreen")

    object UserProfileScreen : Destinations("profileScreen/{userId}") {
        fun createRoute(userId: String) = "profileScreen/$userId"
    }

    object PostDetailScreen : Destinations("postDetailScreen/{userId}/{postId}/{userName}") {
        fun createRoute(userId: String, postId: String, userName: String): String {
            return "postDetailScreen/$userId/$postId/$userName"
        }
    }

    object AddPostScreen : Destinations("addPostScreen/{userId}") {
        fun createRoute(userId: String) = "addPostScreen/$userId"
    }

    object SettingsScreen : Destinations("settings")
}