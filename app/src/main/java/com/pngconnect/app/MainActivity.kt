
package com.pngconnect.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pngconnect.app.navigation.Screen
import com.pngconnect.app.ui.screens.*
import com.pngconnect.app.ui.theme.PNGConnectTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val connected = SupabaseManager().testConnection()
            Log.i(
                "PNGConnect",
                if (connected) {
                    "Supabase connection successful"
                } else {
                    "Supabase connection failed. Check the URL and network."
                }
            )
        }

        setContent {
            PNGConnectTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Welcome.route
                    ) {
                        composable(Screen.Welcome.route) {
                            WelcomeScreen(
                                onGetStarted = {
                                    navController.navigate(Screen.ProfileSetup.route)
                                }
                            )
                        }

                        composable(Screen.ProfileSetup.route) {
                            ProfileSetupScreen(
                                onProfileCompleted = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Welcome.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }

                        composable(Screen.Home.route) {
                            HomeScreen(
                                onStartChat = {
                                    navController.navigate(Screen.Matching.route)
                                },
                                onOpenProfile = {
                                    navController.navigate(Screen.ProfileSetup.route)
                                },
                                onOpenSettings = {
                                    navController.navigate(Screen.Settings.route)
                                },
                                onOpenSafety = {
                                    navController.navigate(Screen.Safety.route)
                                }
                            )
                        }

                        composable(Screen.Matching.route) {
                            MatchingScreen(
                                onMatched = {
                                    navController.navigate(Screen.VideoCall.route)
                                },
                                onCancel = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable(Screen.VideoCall.route) {
                            VideoCallScreen(
                                onEndCall = {
                                    navController.navigate(Screen.CallEnded.route)
                                },
                                onOpenSafety = {
                                    navController.navigate(Screen.Safety.route)
                                }
                            )
                        }

                        composable(Screen.CallEnded.route) {
                            CallEndedScreen(
                                onFindAnother = {
                                    navController.navigate(Screen.Matching.route) {
                                        popUpTo(Screen.Home.route)
                                    }
                                },
                                onReturnHome = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Home.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }

                        composable(Screen.Safety.route) {
                            SafetyScreen(
                                onBack = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable(Screen.Settings.route) {
                            SettingsScreen(
                                onBack = {
                                    navController.popBackStack()
                                },
                                onOpenSafety = {
                                    navController.navigate(Screen.Safety.route)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
