package com.movies.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.movies.domain.movieslist.model.MovieItem
import com.movies.presentation.DialogExtension.showFilteringAndSortingDialog
import com.movies.presentation.SortType
import com.movies.presentation.databinding.FragmentHomeBinding
import com.movies.presentation.home.adapter.HomeMoviesAdapter
import com.movies.presentation.home.viewmodel.HomeRequestState
import com.movies.presentation.home.viewmodel.HomeViewModel
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
            val allMoviesList = viewModel.allMoviesList.value

            val generalMinPriceValue =
                allMoviesList?.minByOrNull { it.price }?.price?.toFloat() ?: 0.0f
            val generalMaxPriceValue =
                allMoviesList?.maxByOrNull { it.price }?.price?.toFloat() ?: 0.0f

            val currentMinPriceValue =
                viewModel.priceRange.value?.first?.toFloat() ?: generalMinPriceValue
            val currentMaxPriceValue =
                viewModel.priceRange.value?.second?.toFloat() ?: generalMaxPriceValue

            requireContext().showFilteringAndSortingDialog(
                currentMinPriceValue = currentMinPriceValue,
                currentMaxPriceValue = currentMaxPriceValue,
                generalMinPriceValue = generalMinPriceValue,
                generalMaxPriceValue = generalMaxPriceValue,
                currentSortType = viewModel.typeOfSorting.value,
                onApplyClicked = { newSortType, newPriceRange ->
                    newSortType?.let {
                        viewModel.updateTypeOfSorting(it)
                    }
                    newPriceRange?.let {
                        viewModel.updatePriceRange(it)
                    }
                })
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
        viewModel.filteredAndSortedMoviesList.observe(viewLifecycleOwner) {
            moviesAdapter?.submitList(it)
        }
        viewModel.typeOfSorting.observe(viewLifecycleOwner) { typeOfSorting ->
            viewModel.allMoviesList.value?.let { moviesList ->
                var newList = sortList(list = moviesList, sortType = typeOfSorting)
                viewModel.priceRange.value?.let { pair ->
                    newList = newList.filter { it.price in pair.first..pair.second }
                }
                viewModel.updateFilteredAndSortedMoviesList(newList)
            }
        }
        viewModel.priceRange.observe(viewLifecycleOwner) { priceRange ->
            viewModel.allMoviesList.value?.let { moviesList ->
                var newList = moviesList.filter { it.price in priceRange.first..priceRange.second }
                viewModel.typeOfSorting.value?.let { typeOfSorting ->
                    newList = sortList(list = newList, sortType = typeOfSorting)
                }
                viewModel.updateFilteredAndSortedMoviesList(newList)
            }
        }
    }

    private fun sortList(list: List<MovieItem>, sortType: SortType) =
        when (sortType) {
            SortType.BY_NAME_FROM_A_TO_Z -> list.sortedBy { it.name }
            SortType.BY_NAME_FROM_Z_TO_A -> list.sortedByDescending { it.name }
            SortType.BY_PRICE_FROM_CHEAP_TO_EXPENSIVE -> list.sortedBy { it.price }
            SortType.BY_PRICE_FROM_EXPENSIVE_TO_CHEAP -> list.sortedByDescending { it.price }
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
        viewModel.updateFilteredAndSortedMoviesList(movies)
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