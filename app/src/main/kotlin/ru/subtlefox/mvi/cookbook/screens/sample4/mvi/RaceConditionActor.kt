package ru.subtlefox.mvi.cookbook.screens.sample4.mvi


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.subtlefox.mvi.cookbook.screens.sample4.data.LikesRemoteRepository
import ru.subtlefox.mvi.cookbook.screens.sample4.mvi.entity.RaceConditionAction
import ru.subtlefox.mvi.cookbook.screens.sample4.mvi.entity.RaceConditionEffect
import ru.subtlefox.mvi.cookbook.screens.sample4.mvi.entity.RaceConditionState
import ru.subtlefox.mvi.flow.GroupMviActor
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RaceConditionActor @Inject constructor(
    private val likesRepository: LikesRemoteRepository
) : GroupMviActor<RaceConditionAction, RaceConditionEffect, RaceConditionState>() {

    private val ioDispatcher = Dispatchers.IO

    override fun invoke(
        action: RaceConditionAction,
        previousState: RaceConditionState
    ): Flow<RaceConditionEffect> {

        return when (action) {
            is RaceConditionAction.Like ->
                like()

            is RaceConditionAction.Unlike ->
                unlike()

            is RaceConditionAction.Share -> flow {
                emit(RaceConditionEffect.DisplayShare(null))
                // Will wait until repository release it's internal lock
                emit(RaceConditionEffect.DisplayShare(likesRepository.getLikes()))
            }.flowOn(ioDispatcher)
        }
    }

    private fun like() = callAsync(100)

    private fun unlike() = callAsync(-100)

    private fun callAsync(delta: Int) = flow {
        emit(RaceConditionEffect.Loading())
        val result = suspendCoroutine { continuation ->
            likesRepository.changeLikesAsync(delta) { asyncResult ->
                continuation.resume(asyncResult)
            }
        }
        emit(RaceConditionEffect.Data(result))
    }.flowOn(ioDispatcher)

    ///////

    companion object {
        const val RACE_GROUP_ID = 100
    }

    override fun transformByGroup(
        actionGroup: Int,
        previousState: RaceConditionState
    ): Flow<RaceConditionAction>.() -> Flow<RaceConditionEffect> = {
        when (actionGroup) {
            RACE_GROUP_ID -> this.flatMapConcat { invoke(it, previousState) }
            else -> this.flatMapMerge { invoke(it, previousState) }
        }
    }

    override fun getGroup(action: RaceConditionAction): Int {
        return when (action) {
            is RaceConditionAction.Like, is RaceConditionAction.Unlike -> RACE_GROUP_ID
            else -> NO_OP_GROUP
        }
    }
}
