package com.movies.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.movies.domain.movieslist.model.MovieItem
import com.movies.presentation.databinding.FragmentHomeBinding
import com.movies.presentation.home.adapter.HomeMoviesAdapter
import com.movies.presentation.home.viewmodel.HomeRequestState
import com.movies.presentation.home.viewmodel.HomeViewModel
import com.movies.presentation.showFilteringAndSortingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel>()

    private var moviesAdapter: HomeMoviesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeStates()
        initViews()
    }

    private fun initViews() {
        binding.filteringAndSortingButton.setOnClickListener {
            val minPriceItem =
                viewModel.moviesList.value?.minByOrNull { it.price }?.price?.toFloat() ?: 0.0f
            val maxPriceItem =
                viewModel.moviesList.value?.maxByOrNull { it.price }?.price?.toFloat() ?: 0.0f
            requireContext().showFilteringAndSortingDialog(minPriceItem, maxPriceItem)
        }
    }

    private fun observeStates() {
        viewModel.homeRequestState.observe(viewLifecycleOwner) {
            when (it) {
                is HomeRequestState.Error -> showErrorMessage()
                is HomeRequestState.Successful -> showMoviesList(it.movieItems)
                HomeRequestState.Loading -> showLoading()
            }
        }
        viewModel.moviesList.observe(viewLifecycleOwner) {
            moviesAdapter?.submitList(it)
        }
    }

    private fun showErrorMessage() {
        binding.apply {
            binding.apply {
                loading.visibility = View.GONE
                moviesRv.visibility = View.GONE
                filteringAndSortingButton.visibility = View.GONE
                errorLayout.apply {
                    root.visibility = View.VISIBLE
                    tryAgainButton.setOnClickListener {
                        viewModel.loadMoviesList()
                    }
                }
            }
        }
    }

    private fun showMoviesList(movies: List<MovieItem>) {
        binding.apply {
            loading.visibility = View.GONE
            errorLayout.root.visibility = View.GONE
            moviesRv.visibility = View.VISIBLE
            filteringAndSortingButton.visibility = View.VISIBLE
        }
        viewModel.updateMoviesList(movies)
    }

    private fun showLoading() {
        binding.apply {
            loading.visibility = View.VISIBLE
            errorLayout.root.visibility = View.GONE
            moviesRv.visibility = View.GONE
            filteringAndSortingButton.visibility = View.GONE
        }
    }

    private fun initRecyclerView() {
        moviesAdapter = HomeMoviesAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(
                    it.toInt()
                )
            )
        }
        binding.moviesRv.adapter = moviesAdapter
    }
}