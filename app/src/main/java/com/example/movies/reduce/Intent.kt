package com.example.movies.reduce

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


interface Intent<T> {
    fun reduce(oldState: T): Flow<T>
}

/**
 * DSL function to help build intents from code blocks.
 *
 * NOTE: Magic of extension functions, (T)->T and T.()->T interchangeable.
 */
fun <T> intent(block: T.() -> T) = object : Intent<T> {
    override fun reduce(oldState: T): Flow<T> = flowOf(block(oldState))
}