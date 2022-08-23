package com.pixabay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pixabay.ui.base.ComposeAppTheme
import com.pixabay.ui.detail.DetailScreen
import com.pixabay.ui.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ComposeAppTheme {
                val navController = rememberNavController()
                val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
                    "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
                }

                NavHost(
                    navController = navController,
                    startDestination = "home_screen"
                ) {
                    composable(route = "home_screen") {
                        val model =
                            hiltViewModel<MainViewModel>(viewModelStoreOwner = viewModelStoreOwner)
                        HomeScreen(onNavigate = navController::navigate, viewModel = model)
                    }
                    composable(route = "detail_screen") {
                        val model =
                            viewModel<MainViewModel>(viewModelStoreOwner = viewModelStoreOwner)
                        DetailScreen(viewModel = model)
                    }
                }
            }
        }
    }
}