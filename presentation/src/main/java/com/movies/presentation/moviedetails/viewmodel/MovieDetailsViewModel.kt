package com.movies.presentation.moviedetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.domain.moviesdetails.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val _detailsRequestState = MutableLiveData<DetailsRequestState>()
    val detailsRequestState: LiveData<DetailsRequestState> = _detailsRequestState

    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _detailsRequestState.postValue(DetailsRequestState.Loading)
            getMovieDetailsUseCase.invoke(
                movieId = movieId,
                onSuccess = {
                    _detailsRequestState.postValue(DetailsRequestState.Successful(it))
                },
                onError = {
                    _detailsRequestState.postValue(DetailsRequestState.Error(it))
                }
            )
        }
    }
}