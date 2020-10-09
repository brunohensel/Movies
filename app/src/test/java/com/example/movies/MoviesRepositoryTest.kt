package com.example.movies

import com.example.movies.data.repository.MoviesApi
import com.example.movies.data.repository.MoviesRepository
import com.example.movies.data.room.CacheMapper
import com.example.movies.data.room.MovieDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MoviesRepositoryTest {

    @Mock
    lateinit var api: MoviesApi

    @Mock
    lateinit var moviesDao: MovieDao

    @Mock
    lateinit var cacheMapper: CacheMapper

    private lateinit var repository: MoviesRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = MoviesRepository(api, moviesDao, cacheMapper)
    }

    @Test
    fun fetchMovies() = runBlockingTest {
        val movies = repository.fetchMovie()
        
    }
}