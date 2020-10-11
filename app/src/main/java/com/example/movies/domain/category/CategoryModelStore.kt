package com.example.movies.domain.category

import com.example.movies.domain.movie.MovieSyncState
import com.example.movies.reduce.FlowModelStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf

@ExperimentalCoroutinesApi
object CategoryModelStore : FlowModelStore<CategoryState>(
    flowOf(
        CategoryState(
            movies = emptyList(),
            syncState = MovieSyncState.MovieIdle
        )
    )
)