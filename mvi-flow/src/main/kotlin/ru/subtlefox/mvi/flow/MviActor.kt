package ru.subtlefox.mvi.flow

import kotlinx.coroutines.flow.Flow

/**
 * Actor executes business logic to produce stream of Effects in response to incoming Action.
 *
 * There is no obligation to use previousState, it can be completely ignored
 *
 * Sample usage:
 * ```
 *      override fun invoke(action: Action, previousState: State): Flow<Effect> {
 *          val effectsFlow = when(action) {
 *              is Action.Load ->  {
 *                  flow {
 *                      emit(Effect.LoadingStarted)
 *                      val data = api.fetch()
 *                      emit(Effect.Data(data))
 *                  }
 *              }
 *              is Action.Next -> {
 *                  flowOf(Effect.Submit)
 *              }
 *          }
 *      }
 * ```
 *
 */
fun interface MviActor<Action : Any, Effect : Any, State : Any> : (Action, State) -> Flow<Effect> {
    override fun invoke(action: Action, previousState: State): Flow<Effect>
}
