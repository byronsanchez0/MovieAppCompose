package com.example.movieappcompose.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.rememberAsyncImagePainter
import com.example.movieappcompose.MoviesViewModel
import com.example.movieappcompose.entity.FavMovie
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue


@OptIn(ExperimentalPagerApi::class)
@Composable
fun FavoritesScreen(moviesViewModel: MoviesViewModel) {
    val context = LocalContext.current
    val id = getUserId(context)
    moviesViewModel.getFavMovie(id.toLong())
    val movies by moviesViewModel.movies.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        com.google.accompanist.pager.HorizontalPager(
            count = movies.size,
            modifier = Modifier.fillMaxSize(),
            state = com.google.accompanist.pager.rememberPagerState()
        ) { pageIndex ->
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(pageIndex).absoluteValue
                    lerp(
                        start = 0.50f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                        .also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                }
            ) {
                val item = movies[pageIndex]
                ItemCard(movie = item, moviesViewModel)
            }
        }
    }
}


@Composable
fun ItemCard(movie: FavMovie, moviesViewModel: MoviesViewModel) {
    val delBtn by moviesViewModel.delBtn.collectAsState()

    IconButton(onClick = { moviesViewModel.deleteFavMovie(movie) }) {
        Icon(
            delBtn,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp),
            tint = MaterialTheme.colorScheme.inversePrimary
        )
    }
    Image(
        painter = rememberAsyncImagePainter(movie.poster),
        contentDescription = "Movie Poster",
        modifier = Modifier.size(width = 420.dp, height = 420.dp)
    )
}

private fun getUserId(context: Context): Int {
    val sharedPreferences = context.getSharedPreferences("sp", Context.MODE_PRIVATE)
    return sharedPreferences.getInt("userId", 0)
}
