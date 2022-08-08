package ru.subtlefox.mvi.flow

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import ru.subtlefox.mvi.flow.utils.groupBy

abstract class BaseMviActor<Action : Any, Effect : Any, State : Any> : MviActor<Action, Effect, State> {

//    private companion object {
//        const val CHANNEL_CAPACITY = 32
//    }

//    private fun Flow<Action>.groupByActionType() = flow {
//        val storage = mutableMapOf<Int, SendChannel<Action>>()
//        try {
//            collect { action ->
//                val channel = storage.getOrPut(getActionType(action)) {
//                    Channel<Action>(CHANNEL_CAPACITY).also {
//                        emit(getActionType(action) to it.consumeAsFlow())
//                    }
//                }
//                channel.send(action)
//            }
//        } finally {
//            storage.values.forEach { chain -> chain.close() }
//        }
//    }

    private fun Flow<Action>.groupByActionType() = groupBy(::getActionType)

    override fun Flow<Action>.process(): Flow<Action> {
        return groupByActionType().flatMapMerge { (actionType, actionTypeFlow) ->
            transformByAction(actionType).invoke(actionTypeFlow)
        }
    }

    override fun Flow<Action>.process(previousState: State): Flow<Effect> {
        return groupByActionType().flatMapMerge { (actionType, actionTypeFlow) ->
            transformByAction(actionType, previousState).invoke(actionTypeFlow)
        }
    }


    open fun transformByAction(actionType: Int): Flow<Action>.() -> Flow<Action> = { this }

    open fun transformByAction(actionType: Int, previousState: State): Flow<Action>.() -> Flow<Effect> = { emptyFlow() }

    open fun getActionType(action: Action): Int = 0
}
