package com.example.movies.presentation.categoryFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.movies.R
import com.example.movies.domain.MovieResponseDto
import com.example.movies.domain.category.CategoryModelStore
import com.example.movies.domain.movie.MovieSyncState
import com.example.movies.reduce.ViewEventFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CategoryFragment : Fragment(R.layout.fragment_category), ViewEventFlow<CategoryViewIntents> {

    lateinit var category: String
    private val scope: CoroutineScope = MainScope()
    private val viewModel: CategoryViewModel by viewModels()
    private val categoryAdapter: CategoryAdapter by lazy { CategoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(MOVIE_CATEGORY)?.let {
            category = it
        }

        CategoryModelStore
            .modelState()
            .map {state ->
                when(state.syncState){
                    MovieSyncState.MovieSuccess -> showMovies(state.movies)
                }
            }
            .launchIn(scope)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMovieCategories.adapter = categoryAdapter

        viewEvents()
            .onEach { intents -> viewModel.process(intents) }
            .launchIn(scope)
    }

    private fun showMovies(movies: List<MovieResponseDto>) {
        categoryAdapter.submitList(movies)
    }

    override fun viewEvents(): Flow<CategoryViewIntents> {
        val flows = listOf(
            CategoryViewIntents.FetchMovieByCategory(category)
        )
        return flows.asFlow()
    }

    companion object {
        const val MOVIE_CATEGORY = "MOVIE_CATEGORY"
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}