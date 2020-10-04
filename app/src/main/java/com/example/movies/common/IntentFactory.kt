package com.example.movies.common

interface IntentFactory<E> {
    suspend fun process(viewEvent: E)
}