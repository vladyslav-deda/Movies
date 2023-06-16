package com.movies.presentation.home.viewmodel

import com.movies.domain.movieslist.model.MovieItem

sealed class HomeRequestState {

    object Loading : HomeRequestState()
    class Successful(val movieItems: List<MovieItem>) : HomeRequestState()
    class Error(throwable: Throwable) : HomeRequestState()
}