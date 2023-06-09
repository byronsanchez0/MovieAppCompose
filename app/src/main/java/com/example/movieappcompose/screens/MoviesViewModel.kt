package com.example.movieappcompose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.sharp.Dangerous
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcompose.entity.FavMovie
import com.example.movieappcompose.repo.FavRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel (private val favRepo: FavRepo) : ViewModel(){

    private val favBtnStateFlow = MutableStateFlow<ImageVector>(Icons.Filled.FavoriteBorder)
    private val delBtnStateFlow = MutableStateFlow<ImageVector>(Icons.Sharp.Dangerous)
    private val listFavMovie = MutableStateFlow<List<FavMovie>>(listOf())
    val favBtn: MutableStateFlow<ImageVector> get() = favBtnStateFlow
    val delBtn: MutableStateFlow<ImageVector> get() = delBtnStateFlow
    val movies: StateFlow<List<FavMovie>> get() = listFavMovie

    fun addtoFavMovie(movie: Movie, userId: Long) {

        viewModelScope.launch(Dispatchers.IO) {
            val favMovie = FavMovie(
                movie.id,
                movie.title,
                movie.poster,
                movie.year,
                userId,
            )
            favRepo.addFavMovie(favMovie)
        }
    }
    fun deleteFavMovie(movie: FavMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            favRepo.deleteFavMovie(movie)
            getFavMovie(movie.userId)
        }
    }
    fun getFavMovie(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val favcharacters = favRepo.getFavMovie(userId)
            listFavMovie.emit(favcharacters)
        }
    }
}
