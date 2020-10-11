package com.example.movies.presentation.categoryFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.movies.R
import com.example.movies.reduce.ViewEventFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class CategoryFragment : Fragment(R.layout.fragment_category), ViewEventFlow<CategoryViewIntents> {

    lateinit var category: String
    private val scope: CoroutineScope = MainScope()
    private val viewModel: CategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(MOVIE_CATEGORY)?.let {
            category = it
        }
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
}