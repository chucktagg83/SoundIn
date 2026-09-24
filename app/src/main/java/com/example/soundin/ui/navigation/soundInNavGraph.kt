package com.example.soundin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.soundin.ui.screens.LoginScreen
import com.example.soundin.ui.screens.RegisterScreen
import com.example.soundin.ui.screens.MainScreen

@Composable
fun soundInNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = soundInRoutes.LOGIN
    ) {

        // LOGIN SCREEN
        composable(soundInRoutes.LOGIN) {

            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(soundInRoutes.REGISTER)
                },

                onLoginSuccess = {
                    navController.navigate(soundInRoutes.MAIN) {

                        popUpTo(soundInRoutes.LOGIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // REGISTER SCREEN
        composable(soundInRoutes.REGISTER) {

            RegisterScreen(
                onNavigateToLogin = {

                    navController.navigate(soundInRoutes.LOGIN) {

                        popUpTo(soundInRoutes.LOGIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // MAIN SCREEN
        composable(soundInRoutes.MAIN) {
            MainScreen()
        }
    }
}