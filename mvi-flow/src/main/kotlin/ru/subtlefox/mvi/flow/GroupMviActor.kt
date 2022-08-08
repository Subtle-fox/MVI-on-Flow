package ru.subtlefox.mvi.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import ru.subtlefox.mvi.flow.utils.groupBy

/**
 * Group Actor add ability to apply different strategy to each group
 *
 * Sample usage:
 * ```
 *      override fun invoke(action: Action, previousState: State): Flow<Effect> {
 *          val effectsFlow = when(action) {
 *              is Action.Like ->  like()
 *              is Action.Unlike ->  unlike()
 *              is Action.NonBlocking -> sendAnalytics()
 *          }
 *      }
 *
 *          override fun transformByGroup(actionGroup: Int, previousState: State ): Flow<Action>.() -> Flow<Effect> = {
 *              when (actionGroup) {
 *                  RACE_CONDITION_GROUP_ID -> this.flatMapConcat { invoke(it, previousState) }
 *                  else -> this.flatMapMerge { invoke(it, previousState) }
 *              }
 *          }
 *
 *          override fun getGroup(action: RaceConditionAction): Int {
 *              return when (action) {
 *                  is Like, is Unlike -> RACE_CONDITION_GROUP_ID
 *                  else -> NO_OP_GROUP
 *              }
 *          }
 * ```
 *
 */
abstract class GroupMviActor<Action : Any, Effect : Any, State : Any> : MviActor<Action, Effect, State> {
    companion object {
        const val NO_OP_GROUP = -1
    }

    override fun Flow<Action>.process(previousState: State): Flow<Effect> {
        return groupBy(::getGroup)
            .flatMapMerge { (actionGroup, groupFlow) ->
                groupFlow.run(transformByGroup(actionGroup, previousState))
            }
    }

    abstract fun transformByGroup(actionGroup: Int, previousState: State): Flow<Action>.() -> Flow<Effect>

    abstract fun getGroup(action: Action): Int
}
