package com.example.movies.model

import com.example.movies.common.FlowModelStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf

@ExperimentalCoroutinesApi
object MovieModelStore :
    FlowModelStore<MovieState>(flowOf(MovieState(movies = emptyList(), MovieSyncState.MovieIdle)))