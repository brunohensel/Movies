package com.example.movies.common

import kotlinx.coroutines.flow.Flow


interface ViewEventFlow<E> {
    fun viewEvents(): Flow<E>
}