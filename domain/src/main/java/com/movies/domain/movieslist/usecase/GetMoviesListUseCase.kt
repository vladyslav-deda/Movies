package com.movies.domain.movieslist.usecase

import com.movies.domain.movieslist.MoviesListRepository
import com.movies.domain.movieslist.model.MovieItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetMoviesListUseCase(private val moviesListRepository: MoviesListRepository) {

    suspend operator fun invoke(
        onSuccess: (List<MovieItem>) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            moviesListRepository.getMoviesList(onSuccess, onError)
        }
    }
}