package com.example.movies.domain.detail

import com.example.movies.domain.movie.CacheMovieDto
import com.example.movies.domain.movie.MovieSyncState
import com.example.movies.reduce.FlowModelStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf

@ExperimentalCoroutinesApi
object DetailModelStore :
    FlowModelStore<DetailState>(
        flowOf(
            DetailState(
                movie = CacheMovieDto.empty,
                syncState = MovieSyncState.MovieIdle
            )
        )
    )