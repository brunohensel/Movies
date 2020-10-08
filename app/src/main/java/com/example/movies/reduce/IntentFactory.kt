package com.example.movies.reduce

interface IntentFactory<E> {
    suspend fun process(viewEvent: E)
}