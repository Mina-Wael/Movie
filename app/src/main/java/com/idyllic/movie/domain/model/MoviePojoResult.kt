package com.idyllic.movie.domain.model

data class MoviePojoResult(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
