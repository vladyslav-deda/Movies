package com.movies.data.moviedetails

import com.movies.data.moviedetails.model.MovieDetailsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDetailsService {

    @GET("movieDetails?")
    suspend fun getMovieDetails(
        @Query("id") id: Int
    ): Response<MovieDetailsDto>
}