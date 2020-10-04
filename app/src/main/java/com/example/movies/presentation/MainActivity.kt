package com.example.movies.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.movies.R
import com.example.movies.common.ViewEventFlow
import com.example.movies.model.MovieModelStore
import com.example.movies.viewmodel.MovieIntents
import com.example.movies.viewmodel.MovieSyncState
import com.example.movies.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ViewEventFlow<MovieIntents> {

    private val viewModel: MoviesViewModel by viewModels()
    private val scope: CoroutineScope = MainScope()

    @FlowPreview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewEvents()
            .onEach { intents -> viewModel.process(intents) }
            .launchIn(scope)

        MovieModelStore
            .modelState()
            .map { movieState ->
                when (movieState.syncState) {
                    is MovieSyncState.MovieLoading -> Log.w("Loading", "Loading")
                    is MovieSyncState.MovieIdle -> Log.w("Idle", "Idle")
                    is MovieSyncState.MovieFailure -> Log.e("Error", movieState.syncState.message)
                    is MovieSyncState.MovieSuccess -> Log.w("Success", "${movieState.movies}")
                }
            }
            .launchIn(scope)
/*        viewModel.state.observe(this) { movieState ->
            when (movieState) {
                is MovieLoading -> Log.w("Loading", "Loading")
                is MovieSuccess -> Log.w("Success", "${movieState.movies}")
                is MovieFailure -> Log.e("Error", movieState.message)
            }
        }*/
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    override fun viewEvents(): Flow<MovieIntents> {
        val flows = listOf(
            MovieIntents.FetchMovies
        )
        return flows.asFlow()
    }
}