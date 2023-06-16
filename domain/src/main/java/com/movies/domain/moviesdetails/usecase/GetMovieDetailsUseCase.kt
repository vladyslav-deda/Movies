package com.movies.domain.moviesdetails.usecase

import com.movies.domain.moviesdetails.MoviesDetailsRepository
import com.movies.domain.moviesdetails.model.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetMovieDetailsUseCase(private val repository: MoviesDetailsRepository) {

    suspend operator fun invoke(
        movieId: Int,
        onSuccess: (MovieDetails) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            repository.getMovieDetails(movieId, onSuccess, onError)
        }
    }
}