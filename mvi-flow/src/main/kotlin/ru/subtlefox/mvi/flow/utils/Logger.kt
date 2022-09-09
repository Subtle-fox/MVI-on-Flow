package ru.subtlefox.mvi.flow.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import java.util.concurrent.CancellationException

interface Logger {
    fun i(tag: String, message: String, e: Throwable? = null)

    fun d(tag: String, message: String, e: Throwable? = null)

    fun e(tag: String, message: String, e: Throwable? = null)

    fun v(tag: String, message: String, e: Throwable? = null)
}

internal fun <T : Any> Flow<T>.logMviFeature(tag: String): Flow<T> = this
    .onStart {
        logD(tag, "subscribed")
    }
    .onEach {
        it.logValue(tag, "state")
    }
    .onCompletion { error ->
        when (error) {
            null -> logD(tag, "completed")
            is CancellationException -> logD(tag, "cancelled")
            else -> logE(tag, error, "failed with: ${error.message}")
        }
    }

internal fun <T : Any> T.logValue(tag: String, method: String) = also {
    logD(tag) { "$method [${Thread.currentThread().name}] > $this" }
}


////////////////////////
//// Simple logging ////
///////////////////////
private fun logD(tag: String, body: () -> String) = println("$tag: ${body.invoke()}")

private fun logD(tag: String, message: String) = println("$tag: $message")

private fun logE(tag: String, error: Throwable, message: String) {
    System.err.println("$tag: $message")
    error.printStackTrace()
}
