package com.movies.di

import com.movies.data.moviedetails.MovieDetailsService
import com.movies.data.moviedetails.MoviesDetailsRepositoryImpl
import com.movies.domain.moviesdetails.MoviesDetailsRepository
import com.movies.domain.moviesdetails.usecase.GetMovieDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MovieDetailsModule {

    @ViewModelScoped
    @Provides
    fun provideMoviesDetailsRepository(movieDetailsService: MovieDetailsService): MoviesDetailsRepository =
        MoviesDetailsRepositoryImpl(movieDetailsService)

    @ViewModelScoped
    @Provides
    fun provideGetMovieDetailsUseCase(
        repository: MoviesDetailsRepository
    ): GetMovieDetailsUseCase = GetMovieDetailsUseCase(repository)
}