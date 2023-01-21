package com.idyllic.movie.ui.details

import androidx.lifecycle.ViewModel
import com.idyllic.movie.domain.repository.RepositoryIntr
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(val repo: RepositoryIntr) : ViewModel() {

}