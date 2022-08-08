package ru.subtlefox.mvi.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import ru.subtlefox.mvi.flow.utils.groupBy

abstract class GroupMviActor<Action : Any, Effect : Any, State : Any> : MviActor<Action, Effect, State> {
    companion object {
        const val NO_OP_GROUP = -1
    }

    override fun Flow<Action>.process(previousState: State): Flow<Effect> {
        return groupBy(::getGroup)
            .flatMapMerge { (actionType, groupFlow) ->
                groupFlow.run(transformByAction(actionType, previousState))
            }
    }

    abstract fun transformByAction(actionType: Int, previousState: State): Flow<Action>.() -> Flow<Effect>

    open fun getGroup(action: Action): Int = NO_OP_GROUP
}
