package com.idyllic.movie.domain.model

data class TopRatedMovie(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
