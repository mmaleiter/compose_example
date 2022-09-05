package com.pixabay.ui.home

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.pixabay.MainViewModel
import com.pixabay.ui.base.Resource
import com.pixabay.ui.base.ScrollableAppBar
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val state = viewModel.imageList.value

    val scrollState = rememberLazyListState()
//    val scrollUpState = viewModel.scrollUp.observeAsState()

//    viewModel.updateScrollPosition(scrollState.firstVisibleItemIndex)


    var refreshing by remember { mutableStateOf(false) }
    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(2000)
            refreshing = false
        }
    }

    val toolbarHeight = 72.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
//        ScrollableAppBar(title = "Sach mal du", scrollUpState = scrollUpState)
//        SearchWidget()


        Column {
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
//                            .weight(1f)
                                .padding(16.dp)

                                .fillMaxWidth(),
                        ) {
                            item {
                                Spacer(modifier = Modifier.height(72.dp))
                            }
                            items(state.data.orEmpty()) { pixabayItem ->
                                PixabayListItem(pixabayItem, viewModel, onNavigate)
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                            item {  SearchWidget() }
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
        }



        Box(
            modifier = Modifier
                .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) },
//            title = { Text("toolbar offset is ${toolbarOffsetHeightPx.value}") }
        ) {
            SearchWidget()
        }


    }
}