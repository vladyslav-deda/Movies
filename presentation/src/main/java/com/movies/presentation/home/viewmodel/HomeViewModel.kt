package com.movies.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.domain.movieslist.model.MovieItem
import com.movies.domain.movieslist.usecase.GetMoviesListUseCase
import com.movies.presentation.SortByValues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesListUseCase: GetMoviesListUseCase
) : ViewModel() {

    private val _homeRequestState = MutableLiveData<HomeRequestState>()
    val homeRequestState: LiveData<HomeRequestState> = _homeRequestState

    private val _moviesList = MutableLiveData<List<MovieItem>>()
    val moviesList: LiveData<List<MovieItem>> = _moviesList

    private val _typeOfSorting = MutableLiveData<SortByValues>()
    val typeOfSorting: LiveData<SortByValues> = _typeOfSorting

    init {
        loadMoviesList()
    }

    fun loadMoviesList() {
        viewModelScope.launch {
            _homeRequestState.postValue(HomeRequestState.Loading)
            getMoviesListUseCase.invoke(
                onSuccess = {
                    _homeRequestState.postValue(HomeRequestState.Successful(it))
                },
                onError = {
                    _homeRequestState.postValue(HomeRequestState.Error(it))
                }
            )
        }
    }

    fun updateMoviesList(movies: List<MovieItem>) = _moviesList.postValue(movies)

    fun updateTypeOfSorting(type: SortByValues) = _typeOfSorting.postValue(type)
}