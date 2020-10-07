package com.example.movies.data.repository

import com.example.movies.data.room.CacheMapper
import com.example.movies.data.room.MovieDao
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val api: MoviesApi,
    private val movieDao: MovieDao,
    private val cacheMapper: CacheMapper
) {
    /** flow {} builder function to construct arbitrary flows from sequential calls to emit function.*/
    suspend fun fetchMovies() =
        flow {
            emit(MovieState.MovieLoading)
            try {
                val moviesResponse = api.fetchMovies()
                moviesResponse.forEach { movieDao.insert(cacheMapper.mapToEntity(it)) }
                val cacheMovieDto = movieDao.get()
                emit(MovieState.MovieSuccess(cacheMapper.mapFromEntityList(cacheMovieDto)))
            } catch (e: Exception) {
                emit(MovieState.MovieFailure(e))
            }
        }
}