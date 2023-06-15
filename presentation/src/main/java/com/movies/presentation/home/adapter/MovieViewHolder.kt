package com.movies.presentation.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.movies.domain.movieslist.model.MovieItem
import com.movies.presentation.databinding.MovieItemBinding

class MovieViewHolder(
    itemView: View,
    private val clickListener: (movieId: String) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val binding = MovieItemBinding.bind(itemView)

    fun bind(data: MovieItem) {
        binding.apply {
            movieName.text = data.name
            moviePrice.text = data.price.toString()
        }
        itemView.setOnClickListener {
            clickListener(data.id)
        }
    }
}