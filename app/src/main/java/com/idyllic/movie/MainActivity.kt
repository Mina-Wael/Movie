package com.idyllic.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.idyllic.movie.ui.ActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: ActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listenToMovieStateFlow()
        viewModel.getTopRatedMovies()

    }


    private fun listenToMovieStateFlow() {
        lifecycleScope.launchWhenCreated {
            viewModel.movieStateFlow.onEach {
                Log.i("TAG", "listenToMovieStateFlow: ${it.size}")
            }
        }
    }
}