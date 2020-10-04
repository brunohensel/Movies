package com.example.movies.model

import com.example.movies.common.FlowModelStore
import com.example.movies.viewmodel.MovieState
import com.example.movies.viewmodel.MovieSyncState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
object MovieModelStore :
    FlowModelStore<MovieState>(MovieState(movies = emptyList(), MovieSyncState.MovieIdle))