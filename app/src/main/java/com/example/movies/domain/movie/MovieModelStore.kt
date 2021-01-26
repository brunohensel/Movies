package com.example.movies.domain.movie

import com.example.movies.reduce.FlowModelStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf

@ExperimentalCoroutinesApi
object MovieModelStore :
    FlowModelStore<MovieState>(flowOf(MovieState(movies = emptyList(), MovieSyncState.MovieIdle)))
