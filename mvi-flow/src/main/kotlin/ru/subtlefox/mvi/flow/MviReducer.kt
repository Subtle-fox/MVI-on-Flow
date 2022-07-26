package ru.subtlefox.mvi.flow

/**
 * Reducer convert previous State to the new one based on Effect

 * There is no obligation to use previousState, it can be completely ignored
 *
 * Sample usage:
 * ```
 *      override fun invoke(effect: Effect, previousState: State): Effect {
 *          when(effect) {
 *              is Action.Loading -> State.Loading
 *              is Action.Loaded -> previousState.copy(data = effect.data)
 *              is Action.Error -> previousState.copy(data = null, error = true)
 *          }
 *      }
 * ```
 */

fun interface MviReducer<Effect, State> : suspend (Effect, State) -> State {
    override suspend fun invoke(effect: Effect, previousState: State): State
}
