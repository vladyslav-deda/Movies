package com.movies.data

import com.movies.data.movieslist.model.MovieDto
import com.movies.domain.movieslist.model.MovieItem

object MovieMapper {

    fun List<MovieDto>.asMovieItemList() = this.map { MovieItem(it.id, it.name, it.price) }
}