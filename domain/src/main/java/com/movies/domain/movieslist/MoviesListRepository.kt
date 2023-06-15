package com.movies.domain.movieslist

import com.movies.domain.movieslist.model.MovieItem

interface MoviesListRepository {

    suspend fun getMoviesList(
        onSuccess: (List<MovieItem>) -> Unit,
        onError: (throwable: Throwable) -> Unit
    )
}