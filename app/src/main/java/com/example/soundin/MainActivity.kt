package com.example.soundin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.soundin.ui.navigation.SoundInNavGraph
import com.example.soundin.ui.screens.LoginContent
import com.example.soundin.ui.screens.LoginScreen
import com.example.soundin.ui.theme.SoundInTheme
import com.example.soundin.ui.viewmodel.UserSessionViewModel
import kotlinx.coroutines.CoroutineScope

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val sessionViewModel: UserSessionViewModel = viewModel()
            SoundInTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SoundInNavGraph(
                        navController = navController,
                        sessionViewModel = sessionViewModel
                    )
                }
            }
        }
    }
}