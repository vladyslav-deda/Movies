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
//        val movieList = listOf(
//            MovieItem("0", "The Shawshank Redemption", 10),
//            MovieItem("1", "The Godfather", 15),
//            MovieItem("2", "The Dark Knight", 12)
//        )
//        onSuccess(movieList)
    }
}