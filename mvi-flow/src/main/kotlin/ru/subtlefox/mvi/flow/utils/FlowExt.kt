package ru.subtlefox.mvi.flow.utils

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow

private const val CHANNEL_CAPACITY = 32

fun <K, V> Flow<V>.groupBy(keyFunc: (V) -> K) = flow {
    val storage = mutableMapOf<K, SendChannel<V>>()
    try {
        collect { action ->
            val groupChannel = storage.getOrPut(keyFunc(action)) {
                Channel<V>(CHANNEL_CAPACITY).also {
                    emit(keyFunc(action) to it.consumeAsFlow())
                }
            }
            groupChannel.send(action)
        }
    } finally {
        storage.values.forEach { chain -> chain.close() }
    }
}
