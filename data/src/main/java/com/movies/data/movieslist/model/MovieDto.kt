package com.movies.data.movieslist.model

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Int
)
