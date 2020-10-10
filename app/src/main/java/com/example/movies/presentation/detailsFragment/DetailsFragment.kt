package com.example.movies.presentation.detailsFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.movies.R
import com.example.movies.domain.MovieResponseDto

class DetailsFragment : Fragment(R.layout.fragment_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgumentsFromPrevFragment()
    }

    private fun getArgumentsFromPrevFragment() {
        arguments?.getParcelable<MovieResponseDto>(MOVIE_DETAIL)?.let {
            Log.i("Bundle passed", "$it")
        }
    }

    companion object {
        const val MOVIE_DETAIL = "MOVIE_DETAIL"
    }
}