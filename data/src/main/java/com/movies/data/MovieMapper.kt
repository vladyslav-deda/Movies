package com.movies.data

import com.movies.data.moviedetails.model.MovieDetailsDto
import com.movies.data.movieslist.model.MovieDto
import com.movies.domain.moviesdetails.model.MovieDetails
import com.movies.domain.movieslist.model.MovieItem

object MovieMapper {

    fun List<MovieDto>.asMovieItemList() = this.map { MovieItem(it.id, it.name, it.price) }

    fun MovieDetailsDto.asMovieDetails() = MovieDetails(image, meta, name, price, rating, synopsis)
}