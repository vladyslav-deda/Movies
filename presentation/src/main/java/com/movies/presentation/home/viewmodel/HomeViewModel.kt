package com.movies.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.domain.movieslist.model.MovieItem
import com.movies.domain.movieslist.usecase.GetMoviesListUseCase
import com.movies.presentation.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesListUseCase: GetMoviesListUseCase
) : ViewModel() {

    private val _homeRequestState = MutableLiveData<HomeRequestState>()
    val homeRequestState: LiveData<HomeRequestState> = _homeRequestState

    private val _filteredAndSortedMoviesList = MutableLiveData<List<MovieItem>>()
    val filteredAndSortedMoviesList: LiveData<List<MovieItem>> = _filteredAndSortedMoviesList

    private val _allMoviesList = MutableLiveData<List<MovieItem>>()
    val allMoviesList: LiveData<List<MovieItem>> = _allMoviesList

    private val _typeOfSorting = MutableLiveData<SortType>()
    val typeOfSorting: LiveData<SortType> = _typeOfSorting

    private val _priceRange = MutableLiveData<Pair<Int, Int>>()
    val priceRange: LiveData<Pair<Int, Int>> = _priceRange

    init {
        loadMoviesList()
    }

    fun loadMoviesList() {
        viewModelScope.launch {
            _homeRequestState.postValue(HomeRequestState.Loading)
            getMoviesListUseCase.invoke(
                onSuccess = {
                    _homeRequestState.postValue(HomeRequestState.Successful(it))
                    _allMoviesList.postValue(it)
                },
                onError = {
                    _homeRequestState.postValue(HomeRequestState.Error(it))
                }
            )
        }
    }

    fun updateFilteredAndSortedMoviesList(movies: List<MovieItem>) =
        _filteredAndSortedMoviesList.postValue(movies)

    fun updateTypeOfSorting(type: SortType) = _typeOfSorting.postValue(type)

    fun updatePriceRange(priceRange: Pair<Int, Int>) = _priceRange.postValue(priceRange)
}