package com.movies.data.moviedetails

import com.movies.data.MovieMapper.asMovieDetails
import com.movies.domain.moviesdetails.MoviesDetailsRepository
import com.movies.domain.moviesdetails.model.MovieDetails
import kotlinx.coroutines.delay

class MoviesDetailsRepositoryImpl(
    private val movieDetailsService: MovieDetailsService
) : MoviesDetailsRepository {

    override suspend fun getMovieDetails(
        movieId: Int,
        onSuccess: (MovieDetails) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        val response = movieDetailsService.getMovieDetails(movieId)
        if (response.code() == 200 && response.body() != null) {
            onSuccess(response.body()!!.asMovieDetails())
        } else {
            onError(Exception("Could not load movie detail"))
        }
    }
}