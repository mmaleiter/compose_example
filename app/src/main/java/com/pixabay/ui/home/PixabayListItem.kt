package com.pixabay.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.pixabay.MainViewModel
import com.pixabay.R
import com.pixabay.ui.base.lightGreyAlpha
import com.pixabay.ui.base.primaryCharcoal

@Composable
fun PixabayListItem(
    pixabayItem: PixBayUiListItem,
    viewModel: MainViewModel,
    onNavigate: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        val isFavourite = pixabayItem.isFavourite
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .clickable {
                    viewModel.showDetailScreen(pixabayItem)
                    onNavigate("detail_screen")
                }
                .background(
                    color = if (isSystemInDarkTheme()) primaryCharcoal else lightGreyAlpha,
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(144.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))
//                AsyncImage(
//                    imageLoader = provideImageLoader(context),
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(pixabayItem.pixBayItem.largeImageURL)
//                        .crossfade(true)
//                        .build(),
//                    placeholder = painterResource(android.R.drawable.ic_dialog_info),
//                    contentDescription = stringResource(android.R.string.cancel),
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .size(96.dp)
//                        .width(144.dp)
//                        .height(144.dp)
//                        .clip(CircleShape)
//                        .border(1.5.dp, MaterialTheme.colors.background, CircleShape)
//                )

                val painter = rememberAsyncImagePainter(
                    model = pixabayItem.pixBayItem.largeImageURL,
                )
                val painterState = painter.state

                Box(modifier = Modifier.size(96.dp)) {
                    Image(
                        painter = painter,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .border(1.5.dp, MaterialTheme.colors.background, CircleShape),
                        contentDescription = "entry.pokemonName",
                        contentScale = ContentScale.Crop,
                    )

                    if (painterState is AsyncImagePainter.State.Loading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.size(96.dp)
                        )
                    }
                }

                Column(modifier = Modifier.fillMaxWidth(0.75f)) {
                    Text(
                        text = pixabayItem.pixBayItem.tags,
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        text = pixabayItem.pixBayItem.type,
                        modifier = Modifier.padding(start = 16.dp),
                        color = Color.Red
                    )
                    Text(
                        text = pixabayItem.pixBayItem.user,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                    )
                }
                IconButton(onClick = { viewModel.toggleFavourite(pixabayItem) }) {
                    Icon(
                        painter =
                        if (isFavourite) painterResource(id = R.drawable.ic_favorite_filled)
                        else painterResource(id = R.drawable.ic_favorite_border),
                        contentDescription = "Search"
                    )
                }
            }
        }
    }
}