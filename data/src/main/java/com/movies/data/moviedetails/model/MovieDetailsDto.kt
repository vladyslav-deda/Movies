package com.movies.data.moviedetails.model

import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    @SerializedName("image") val image: String,
    @SerializedName("meta")val meta: String,
    @SerializedName("name")val name: String,
    @SerializedName("price")val price: Int,
    @SerializedName("rating")val rating: String,
    @SerializedName("synopsis")val synopsis: String
)
