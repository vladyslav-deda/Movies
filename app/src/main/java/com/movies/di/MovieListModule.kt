package com.movies.di

import com.movies.data.movieslist.MoviesListRepositoryImpl
import com.movies.data.movieslist.MoviesListService
import com.movies.domain.movieslist.MoviesListRepository
import com.movies.domain.movieslist.usecase.GetMoviesListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MovieListModule {

    @ViewModelScoped
    @Provides
    fun provideMoviesListRepository(moviesListService: MoviesListService): MoviesListRepository =
        MoviesListRepositoryImpl(moviesListService)

    @ViewModelScoped
    @Provides
    fun provideGetMoviesListUseCase(moviesListRepository: MoviesListRepository): GetMoviesListUseCase =
        GetMoviesListUseCase(moviesListRepository)
}