package com.example.movies.presentation.categoryFragment

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.movies.data.repository.MoviesRepository
import com.example.movies.domain.category.CategoryModelStore
import com.example.movies.domain.category.CategoryState
import com.example.movies.reduce.Intent
import com.example.movies.reduce.IntentFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class CategoryViewModel @ViewModelInject constructor(
    private val repository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), IntentFactory<CategoryViewIntents> {

    override suspend fun process(viewEvent: CategoryViewIntents) {
        CategoryModelStore.process(toState(viewEvent))
    }

    private fun toState(intents: CategoryViewIntents): Intent<CategoryState> {
        return when (intents) {
            is CategoryViewIntents.FetchMovieByCategory -> repository.fetchMoviesByCategory(intents.category)
        }
    }
}
