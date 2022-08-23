package com.pixabay.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pixabay.MainViewModel
import com.pixabay.ui.base.provideTextStyle


@Composable
fun DetailScreen(viewModel: MainViewModel) {

    val item = viewModel.detailItem.pixBayItem

    Column {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(viewModel.detailItem.pixBayItem.largeImageURL)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(android.R.string.cancel),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(244.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "User: ${item.user}",
            style = provideTextStyle(),
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "Has ${item.likes} likes.",
            style = provideTextStyle(),
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "Has ${item.downloads} downloads.",
            style = provideTextStyle(
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.2.em,),
            modifier = Modifier.padding(16.dp)
        )
    }

}