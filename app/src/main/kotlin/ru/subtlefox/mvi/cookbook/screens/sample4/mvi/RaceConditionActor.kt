package ru.subtlefox.mvi.cookbook.screens.sample4.mvi


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.subtlefox.mvi.cookbook.screens.sample4.data.LikesRemoteRepository
import ru.subtlefox.mvi.cookbook.screens.sample4.mvi.entity.RaceConditionAction
import ru.subtlefox.mvi.cookbook.screens.sample4.mvi.entity.RaceConditionEffect
import ru.subtlefox.mvi.cookbook.screens.sample4.mvi.entity.RaceConditionState
import ru.subtlefox.mvi.flow.MviActor
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RaceConditionActor @Inject constructor(
    private val likesRepository: LikesRemoteRepository
) : MviActor<RaceConditionAction, RaceConditionEffect, RaceConditionState> {

    private val guard = MutexGuardian()
    private val ioDispatcher = Dispatchers.IO

    override fun invoke(
        action: RaceConditionAction,
        previousState: RaceConditionState
    ): Flow<RaceConditionEffect> {

        return when (action) {
            is RaceConditionAction.Like ->
                guard.like()

            is RaceConditionAction.Unlike ->
                guard.unlike()

            is RaceConditionAction.Share -> flow {
                emit(RaceConditionEffect.DisplayShare(null))
                // Will wait until repository release it's internal lock
                emit(RaceConditionEffect.DisplayShare(likesRepository.getLikes()))
            }.flowOn(ioDispatcher)
        }
    }

    private inner class MutexGuardian {
        fun like() = callAsync(100)
        fun unlike() = callAsync(-100)

        /*
            Ensure that this flow will executes sequentially in "atomic" way (sequence "start -> operation -> result")

            flatMap's implementation transforms actions sequentially
         */
        private val mutex = Mutex()

        private fun callAsync(delta: Int) = flow {
            mutex.withLock {
                emit(RaceConditionEffect.Loading())
                val result = suspendCoroutine { continuation ->
                    likesRepository.changeLikesAsync(delta) { asyncResult ->
                        continuation.resume(asyncResult)
                    }
                }
                emit(RaceConditionEffect.Data(result))
            }
        }.flowOn(ioDispatcher)
    }
}