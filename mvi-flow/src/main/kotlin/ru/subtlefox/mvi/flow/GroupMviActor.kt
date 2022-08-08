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
            .flatMapMerge { (actionGroup, groupFlow) ->
                groupFlow.run(transformByAction(actionGroup, previousState))
            }
    }


    abstract fun transformByAction(actionGroup: Int, previousState: State): Flow<Action>.() -> Flow<Effect>

    abstract fun getGroup(action: Action): Int

    interface ActionGroup<Action, State> {
        fun getId(action: Action): Int
        fun getTransformation(actionType: Int, previousState: State)
    }
}
