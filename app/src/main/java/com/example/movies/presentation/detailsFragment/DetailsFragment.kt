package com.example.movies.presentation.detailsFragment

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.domain.detail.DetailModelStore
import com.example.movies.domain.detail.DetailState
import com.example.movies.domain.movie.CacheMovieDto
import com.example.movies.domain.movie.MovieSyncState
import com.example.movies.reduce.ViewEventFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details), ViewEventFlow<DetailViewIntents> {

    private var movieId: Int? = null
    private val viewModel: DetailsViewModel by viewModels()
    private val scope: CoroutineScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt(MOVIE_DETAIL)?.let {
            Log.i("ID", "$it")
            movieId = it
        }

        viewEvents()
            .onEach { intents -> viewModel.process(intents) }
            .launchIn(scope)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DetailModelStore
            .modelState()
            .map { value: DetailState ->
                when (value.syncState) {
                    MovieSyncState.MovieSuccess -> showMovieDetails(value.movie)
                }
            }
            .launchIn(scope)
    }

    override fun viewEvents(): Flow<DetailViewIntents> {
        val flows = listOf(
            DetailViewIntents.FetchDetailMovie(movieId ?: 1)
        )
        return flows.asFlow()
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    private fun showMovieDetails(movie: CacheMovieDto) {
        tvMovieDescription.text = Html.fromHtml(movie.summary)
        Glide.with(this)
            .load(movie.image)
            .centerInside()
            .into(imgMovieDetail)
    }

    companion object {
        const val MOVIE_DETAIL = "MOVIE_DETAIL"
    }
}