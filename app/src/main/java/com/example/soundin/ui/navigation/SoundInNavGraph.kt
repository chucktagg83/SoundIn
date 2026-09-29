package com.example.soundin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.soundin.ui.screens.LoginScreen
import com.example.soundin.ui.screens.MainScreen
import com.example.soundin.ui.screens.RegisterScreen

@Composable
fun SoundInNavGraph(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = SoundInRoutes.LOGIN
    ){
        composable(SoundInRoutes.LOGIN){
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(SoundInRoutes.REGISTER)
                },
                onLoginSuccess ={
                    navController.navigate(SoundInRoutes.MAIN){
                        popUpTo(SoundInRoutes.LOGIN) {inclusive = true}
                    }
                }
            )
        }

        composable(SoundInRoutes.REGISTER){
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(SoundInRoutes.LOGIN)
                }
            )
        }
        composable(SoundInRoutes.MAIN) {
            MainScreen()
        }
    }
}