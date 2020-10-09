package com.example.movies.data.repository

import com.example.movies.data.room.CacheMapper
import com.example.movies.data.room.MovieDao
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
                        val cacheMovieDto = movieDao.get()
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
}