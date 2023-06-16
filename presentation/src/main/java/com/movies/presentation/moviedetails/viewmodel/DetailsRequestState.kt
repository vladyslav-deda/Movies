package com.movies.presentation.moviedetails.viewmodel

import com.movies.domain.moviesdetails.model.MovieDetails

sealed class DetailsRequestState {

    object Loading : DetailsRequestState()
    class Successful(val movieDetails: MovieDetails) : DetailsRequestState()
    class Error(throwable: Throwable) : DetailsRequestState()
}