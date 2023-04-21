package com.pixabay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pixabay.ui.base.BaseTheme
import com.pixabay.ui.base.ScreenOrientationHandler
import com.pixabay.ui.detail.DetailScreen
import com.pixabay.ui.detail.DetailScreenLandscape
import com.pixabay.ui.home.HomeScreen
import com.pixabay.ui.pager.PagerScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseTheme {
                val navController = rememberNavController()
                val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
                    "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
                }
                val viewModel =
                    viewModel<MainViewModel>(viewModelStoreOwner = viewModelStoreOwner)

                val imageList = viewModel.imageList.collectAsState().value.data ?: emptyList()
                val pageCount = viewModel.imageList.collectAsState().value.data?.size ?: 0

                NavHost(
                    navController = navController,
                    startDestination = "home_screen"
                ) {
                    composable(route = "home_screen") {
                        HomeScreen(onNavigate = navController::navigate, viewModel = viewModel)
                    }
                    composable(route = "detail_screen") {
                        ScreenOrientationHandler(landscapeContent = {
                            DetailScreenLandscape(
                                viewModel = viewModel,
                                onNavigate = navController::navigate
                            )
                        },
                            portraitContent = {
                                DetailScreen(
                                    viewModel = viewModel,
                                    onNavigate = navController::navigate
                                )
                            })

                    }
                    composable(route = "view_pager_screen") {
                        PagerScreen(imageList, pageCount)
                    }
                }
            }
        }
    }
}