package com.example.movies.common

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@ExperimentalCoroutinesApi
open class FlowModelStore<S>(startingState: S) : ModelStore<S> {

    /**[Channel] is a non-blocking primitive for communication between a sender and a receiver*/
    /**[ConflatedBroadcastChannel] Broadcasts the most recently sent element to all subscribers*/
    /**[MainScope] Creates the main CoroutineScope for UI components.*/

    private val scope = MainScope()
    private val intents = Channel<Intent<S>>()
    private val store = ConflatedBroadcastChannel(startingState)

    init {
        // Reduce from MainScope()
        scope.launch {
            while (isActive)
            /**Act like a binding between the intents and the store.
             * Who subscribe to that state will get the initial state created by store. Then
             * when a first [intents] is received the reduce function will be called receiving
             * a old value and will generated a new one.*/
                store.offer(intents.receive().reduce(store.value))
        }
    }

    override suspend fun process(intent: Intent<S>) {
        intents.send(intent)
    }

    @FlowPreview
    override fun modelState(): Flow<S> = store.asFlow()

    fun close() {
        intents.close()
        store.close()
        scope.cancel()
    }
}