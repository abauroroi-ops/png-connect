package com.pngconnect.app.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object ProfileSetup : Screen("profile_setup")
    object Home : Screen("home")
    object Matching : Screen("matching")
    object VideoCall : Screen("video_call")
    object CallEnded : Screen("call_ended")
    object Safety : Screen("safety")
    object Settings : Screen("settings")
}
