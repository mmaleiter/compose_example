package com.pixabay.ui.home

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.pixabay.MainViewModel
import com.pixabay.ui.base.Resource
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val state = viewModel.imageList.value
    val scrollState = rememberLazyListState()

    var refreshing by remember { mutableStateOf(false) }
    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(2000)
            refreshing = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (state) {
            is Resource.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Success -> {
                val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = refreshing)
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = { viewModel.executeSearch() },

                ) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(32.dp))
                        }
                        items(state.data.orEmpty()) { pixabayItem ->
                            PixabayListItem(pixabayItem, viewModel, onNavigate)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
            is Resource.Error -> {
                Toast.makeText(
                        context,
                        state.cause?.toString(),
                        Toast.LENGTH_LONG
                    ).show()
            }
        }
        SearchWidget()
    }
}