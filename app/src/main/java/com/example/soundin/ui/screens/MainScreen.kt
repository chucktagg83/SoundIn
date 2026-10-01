package com.example.soundin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.soundin.ui.components.BottomNavigationBar
import com.example.soundin.ui.models.Playlist
import com.example.soundin.ui.navigation.SoundInRoutes
import com.example.soundin.ui.theme.SoundInTheme
import com.example.soundin.ui.viewmodel.UserSessionViewModel

@Composable
fun MainScreen(
    sessionViewModel: UserSessionViewModel,
    onLogout: () -> Unit,
    onNavigateToPlaylistDetail: (Playlist) -> Unit
){
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onNavigate = {route ->
                    navController.navigate(route){
                        popUpTo(navController.graph.startDestinationId){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) // end BottomNavigationBar
        }
    ) {
            paddingValues ->
        NavHost(
            navController = navController,
            startDestination = SoundInRoutes.LIBRARY,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable (SoundInRoutes.LIBRARY) {LibraryScreen()}
            composable(SoundInRoutes.SEARCH) {SearchScreen()}
            composable (SoundInRoutes.PROFILE) {ProfileScreen()}
        }
    }
}

