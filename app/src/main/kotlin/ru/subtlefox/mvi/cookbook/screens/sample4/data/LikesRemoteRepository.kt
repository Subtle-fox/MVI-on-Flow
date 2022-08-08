package ru.subtlefox.mvi.cookbook.screens.sample4.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.concurrent.ConcurrentLinkedQueue
import javax.inject.Inject
import kotlin.concurrent.thread

class LikesRemoteRepository @Inject constructor() {

    private val likesFlow = MutableStateFlow(1_000)

    val likes = likesFlow.asSharedFlow()
    private val lock = Object()

    /*
        Hold the lock until http request will finish
    */
    fun getLikes() = synchronized(lock) {
        likesFlow.value
    }

    /*
        For demonstration purpose: default async api / no coroutines
     */
    fun changeLikesAsync(delta: Int, onFinished: (Int) -> Unit) {
        thread {
            val result = synchronized(lock) {
                longRunningHttpRequest(delta)
            }
            onFinished.invoke(result)
        }
    }

    /*
        If strong ordering of requests needed
     */
    private val requestQueue = ConcurrentLinkedQueue<Int>()
    fun changeLikesWithSequenceQueue(delta: Int, onFinished: (Int) -> Unit) {
        requestQueue.offer(delta)
        thread {
            val result = synchronized(lock) {
                longRunningHttpRequest(requestQueue.poll())
            }
            onFinished.invoke(result)
        }
    }


    private fun longRunningHttpRequest(delta: Int): Int {
        Thread.sleep(2000L)
        likesFlow.value += delta
        return likesFlow.value
    }
}
