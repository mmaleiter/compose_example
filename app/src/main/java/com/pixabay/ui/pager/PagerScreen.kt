package com.pixabay.ui.pager

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.widget.RatingBar
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.pixabay.ui.home.PixBayUiListItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue


@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerScreen(
    imageList: List<PixBayUiListItem>,
) {

    val pagerState = rememberPagerState()

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(4000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                animationSpec = tween(600)
            )
        }
    }

    Column {
        Text(
            text = "Beautiful",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(start = 16.dp, top = 40.dp),
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Black
        )
        Text(
            text = "Images",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(start = 16.dp),
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Black
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .padding(
                    top = 40.dp,
                    bottom = 40.dp
                ),
            count = imageList.size
        ) { pageIndex ->
            Card(
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(pageIndex).absoluteValue
                        lerp(
                            start = ScaleFactor(0.85f, 0.85f),
                            stop = ScaleFactor(1f, 1f),
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale.scaleX
                            scaleY = scale.scaleY
                        }
                    }
                    .fillMaxWidth()
                    .padding(
                        start = 12.dp,
                        end = 12.dp,
                    ),
                shape = RoundedCornerShape(24.dp),
            ) {
                val imageItem = imageList[pageIndex]
                val painter = rememberAsyncImagePainter(
                    model = imageItem.pixBayItem.largeImageURL,
                )
                Box {
                    Image(
                        painter = painter,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentDescription = "entry.pokemonName",
                        contentScale = ContentScale.Crop,
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = imageItem.pixBayItem.user,
                            style = MaterialTheme.typography.h5,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        val ratingBar = RatingBar(
                            LocalContext.current
                        ).apply {
                            rating = imageItem.pixBayItem.likes.toFloat()
                            val color = android.graphics.Color.parseColor("#ff8800")
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                progressDrawable.colorFilter = BlendModeColorFilter(
                                    color,
                                    BlendMode.SRC_ATOP
                                )
                            } else {
                                progressDrawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                            }

                        }
                        AndroidView(
                            factory = { ratingBar },
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = imageItem.pixBayItem.type,
                            style = MaterialTheme.typography.body1,
                            color = Color.White,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
