package com.example.movieappcompose

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.omdbapi.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val omdbApiService: MoviesService by lazy {
        retrofit.create(MoviesService::class.java)
    }

    suspend fun searchMovies(query: String): List<Movie> {
        val apiKey = "34a8034"
        val response = omdbApiService.searchMovies(apiKey, query)
        if (response.isSuccessful) {
            val searchResponse = response.body()
            return searchResponse?.search ?: emptyList()
        } else {
            throw Exception("Failed to search movies: ${response.errorBody()}")
        }
    }
}