package com.pixabay.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pixabay.MainViewModel
import com.pixabay.ui.base.Resource

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val state = viewModel.imageList.value

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
            is Resource.Error -> {
                Toast
                    .makeText(
                        context,
                        state.cause?.toString(),
                        Toast.LENGTH_LONG
                    )
                    .show()
            }
        }
        SearchWidget()
    }
}