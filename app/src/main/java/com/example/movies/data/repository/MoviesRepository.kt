package com.example.movies.data.repository

import com.example.movies.data.room.CacheMapper
import com.example.movies.data.room.MovieDao
import com.example.movies.domain.detail.DetailState
import com.example.movies.domain.movie.MovieState
import com.example.movies.domain.movie.MovieSyncState
import com.example.movies.reduce.Intent
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class MoviesRepository @Inject constructor(
    private val api: MoviesApi,
    private val movieDao: MovieDao,
    private val cacheMapper: CacheMapper
) {
    fun fetchMovie(): Intent<MovieState> {
        return object : Intent<MovieState> {
            override fun reduce(oldState: MovieState): Flow<MovieState> =
                flow {
                    emit(oldState.copy(syncState = MovieSyncState.MovieLoading))
                    try {
                        val moviesResponse = api.fetchMovies()
                        moviesResponse.forEach { movieDao.insert(cacheMapper.mapToEntity(it)) }
                        val cacheMovieDto = movieDao.get().sortedByDescending { it.rating }
                        emit(
                            oldState.copy(
                                movies = cacheMapper.mapFromEntityList(cacheMovieDto),
                                syncState = MovieSyncState.MovieSuccess
                            )
                        )
                    } catch (e: Exception) {
                        emit(oldState.copy(syncState = MovieSyncState.MovieFailure(e)))
                    }
                }.flowOn(IO)
        }
    }

    fun fetchDetailFromLocal(id: Int): Intent<DetailState> {
        return object : Intent<DetailState> {
            override fun reduce(oldState: DetailState): Flow<DetailState> =
                flow {
                    emit(oldState.copy(syncState = MovieSyncState.MovieLoading))
                    val cachedMovie = movieDao.getMovie(id)
                    emit(
                        oldState.copy(
                            movie = cachedMovie,
                            syncState = MovieSyncState.MovieSuccess
                        )
                    )
                }.flowOn(IO)
        }
    }
}