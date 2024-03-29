package com.pixabay.ui.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pixabay.ui.base.Resource
import com.pixabay.ui.theme.provideTextStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenLandscape(
    onNavigate: (String) -> Unit,
    setSearchTerm: (String) -> Unit,
    showDetailScreen: (PixBayUiListItem) -> Unit,
    toggleFavourite: (PixBayUiListItem) -> Unit,
    filterList: StateFlow<List<String>>,
    imageList: StateFlow<Resource<List<PixBayUiListItem>>>,
    commonColor: Color,
    executeSearch: () -> Unit,
) {
        val context = LocalContext.current
        val state = imageList.collectAsStateWithLifecycle().value

        var refreshing by remember { mutableStateOf(false) }
        LaunchedEffect(refreshing) {
            if (refreshing) {
                delay(4000)
                refreshing = false
            }
        }

        val systemUiController = rememberSystemUiController()
        systemUiController.setSystemBarsColor(commonColor)

        val toolbarHeight = 144.dp
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
                            indicatorPadding = PaddingValues(top = 144.dp),
                            state = swipeRefreshState,
                            onRefresh = { executeSearch() },
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                            ) {
                                item {
                                    Spacer(modifier = Modifier.height(144.dp))
                                }
                                items(state.data.orEmpty()) { pixabayItem ->
                                    PixabayListItem(pixabayItem, showDetailScreen, toggleFavourite, onNavigate)
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
            }

            Box(
                modifier = Modifier
                    .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt() - 48) },
            ) {
                Column(modifier = Modifier.background(commonColor)) {
                    LazyRow(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        val itemStrings =
                            imageList.value.data?.
                            map { it.pixBayItem.getTagList()  }?.flatten()?.toSet()?.toList()

                        val itemData = itemStrings?.map { FilterChipData(it) }

                        itemData?.forEach {
                            item {
                                Surface(
                                    color = if (it.isSelected) MaterialTheme.colors.surface
                                    else MaterialTheme.colors.primary,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .border(
                                            width = 1.dp,
                                            color = MaterialTheme.colors.primary,
                                            shape = CircleShape
                                        )
                                        .background(Color.Transparent),
                                    onClick = {

                                    }
                                ) {
                                    Text(
                                        modifier = Modifier.padding(16.dp),
                                        text = it.title,
                                        style = provideTextStyle(letterSpacing = TextUnit.Unspecified)
                                    )
                                }
                            }
                        }
                    }
                    SearchWidget(setSearchText = setSearchTerm, executeSearch = executeSearch)
                }

            }

        }
}