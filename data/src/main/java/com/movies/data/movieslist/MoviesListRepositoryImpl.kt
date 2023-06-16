package com.movies.data.movieslist

import com.movies.data.MovieMapper.asMovieItemList
import com.movies.domain.movieslist.MoviesListRepository
import com.movies.domain.movieslist.model.MovieItem

class MoviesListRepositoryImpl(
    private val moviesListService: MoviesListService
) : MoviesListRepository {

    override suspend fun getMoviesList(
        onSuccess: (List<MovieItem>) -> Unit,
        onError: (throwable: Throwable) -> Unit
    ) {
        val response = moviesListService.getMoviesList()
        if (response.code() == 200 && response.body() != null) {
            onSuccess(response.body()!!.asMovieItemList())
        } else {
            onError(Exception("Could not load movies"))
        }
    }
}