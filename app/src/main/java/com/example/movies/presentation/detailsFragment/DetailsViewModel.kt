package com.example.movies.presentation.detailsFragment

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.movies.data.repository.MoviesRepository
import com.example.movies.domain.detail.DetailModelStore
import com.example.movies.domain.detail.DetailState
import com.example.movies.reduce.Intent
import com.example.movies.reduce.IntentFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class DetailsViewModel @ViewModelInject constructor(
    private val repository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), IntentFactory<DetailViewIntents> {

    override suspend fun process(viewEvent: DetailViewIntents) {
        DetailModelStore.process(toState(viewEvent))
    }

    private fun toState(intents: DetailViewIntents): Intent<DetailState> {
        return when (intents) {
            is DetailViewIntents.FetchDetailMovie -> repository.fetchDetailFromLocal(id = intents.id)
        }
    }
}