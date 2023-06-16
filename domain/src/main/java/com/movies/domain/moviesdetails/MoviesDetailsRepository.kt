package com.movies.domain.moviesdetails

import com.movies.domain.moviesdetails.model.MovieDetails

interface MoviesDetailsRepository {

    suspend fun getMovieDetails(
        movieId: Int,
        onSuccess: (MovieDetails) -> Unit,
        onError: (throwable: Throwable) -> Unit
    )
}