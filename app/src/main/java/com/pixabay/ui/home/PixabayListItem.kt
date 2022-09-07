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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.pixabay.MainViewModel
import com.pixabay.R
import com.pixabay.ui.theme.lightGreyAlpha
import com.pixabay.ui.theme.primaryCharcoal

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PixabayListItem(
    pixabayItem: PixBayUiListItem,
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNavigate: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        val isFavourite = pixabayItem.isFavourite
        val context = LocalContext.current
        val uriHandler = LocalUriHandler.current
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .padding(start = 16.dp)
                ) {

                    Text(
                        text = pixabayItem.pixBayItem.user,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = pixabayItem.pixBayItem.tags,
                        modifier = Modifier.padding(bottom = 8.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )

                    Surface(
                        onClick = { uriHandler.openUri(pixabayItem.pixBayItem.pageURL) },
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colors.primary,
                                shape = CircleShape
                            )
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        Text(
                            text = "Visit web site",
                            color = MaterialTheme.colors.primary,
                        )

                    }
                }
                IconButton(onClick = { viewModel.toggleFavourite(pixabayItem) }) {
                    Icon(
                        painter =
                        if (isFavourite) painterResource(id = R.drawable.ic_favorite_filled)
                        else painterResource(id = R.drawable.ic_favorite_border),
                        tint = MaterialTheme.colors.primary,
                        contentDescription = "Search"
                    )
                }
            }
        }
    }
}