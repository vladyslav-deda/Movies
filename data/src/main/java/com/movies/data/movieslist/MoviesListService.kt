package com.movies.data.movieslist

import com.movies.data.movieslist.model.MovieDto
import retrofit2.Response
import retrofit2.http.GET

interface MoviesListService {

    @GET("movies")
    suspend fun getMoviesList(): Response<List<MovieDto>>
}