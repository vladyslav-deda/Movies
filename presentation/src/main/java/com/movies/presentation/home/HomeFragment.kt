package com.movies.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
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
    }

    private fun observeStates() {
        viewModel.homeRequestState.observe(viewLifecycleOwner) {
            when (it) {
                is HomeRequestState.Error -> Toast.makeText(
                    requireContext(),
                    "ERROR",
                    Toast.LENGTH_SHORT
                ).show()

                HomeRequestState.Loading -> Toast.makeText(
                    requireContext(),
                    "LOADING",
                    Toast.LENGTH_SHORT
                ).show()

                is HomeRequestState.Successful -> moviesAdapter?.submitList(it.movieItems)
            }
        }
    }

    private fun initRecyclerView() {
        moviesAdapter = HomeMoviesAdapter {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        binding.moviesRv.adapter = moviesAdapter
    }

}