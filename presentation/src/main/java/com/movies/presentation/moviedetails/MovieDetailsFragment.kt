package com.movies.presentation.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.movies.domain.moviesdetails.model.MovieDetails
import com.movies.presentation.R
import com.movies.presentation.databinding.FragmentMovieDetailsBinding
import com.movies.presentation.home.viewmodel.HomeRequestState
import com.movies.presentation.moviedetails.viewmodel.DetailsRequestState
import com.movies.presentation.moviedetails.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    private val viewModel by viewModels<MovieDetailsViewModel>()

    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadMovieDetails(args.movieId)
        viewModel.detailsRequestState.observe(viewLifecycleOwner) {
            when (it) {
                is DetailsRequestState.Error -> showErrorMessage()
                is DetailsRequestState.Successful -> showMovieDetails(it.movieDetails)
                DetailsRequestState.Loading -> showLoading()
            }
        }
    }

    private fun showErrorMessage() {
        binding.apply {
            binding.apply {
                loading.visibility = View.GONE
                mainLayout.visibility = View.GONE
                errorLayout.apply {
                    root.visibility = View.VISIBLE
                    tryAgainButton.setOnClickListener {
                        viewModel.loadMovieDetails(args.movieId)
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.apply {
            loading.visibility = View.VISIBLE
            mainLayout.visibility = View.GONE
            errorLayout.root.visibility = View.GONE
        }
    }

    private fun showMovieDetails(movieDetails: MovieDetails) {
        binding.apply {
            loading.visibility = View.GONE
            errorLayout.root.visibility = View.GONE
            mainLayout.visibility = View.VISIBLE
            Glide.with(requireContext())
                .load(movieDetails.image)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(ROUNDING_RADIUS)))
                .error(R.drawable.error_outline)
                .into(movieImage)
            movieName.text = movieDetails.name
            movieMeta.text = movieDetails.meta
            movieRating.text = movieDetails.rating
            moviePrice.text =
                requireContext().getString(R.string.price, movieDetails.price)
            movieSynopsis.text = movieDetails.synopsis
        }
    }

    companion object {
        private const val ROUNDING_RADIUS = 100
    }
}