package ru.subtlefox.mvi.flow

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.AbstractFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import ru.subtlefox.mvi.flow.utils.logMviFeature
import ru.subtlefox.mvi.flow.utils.logValue


/**
 * Base implementation for MVI pattern.
 *
 * Reducer's and EventProducer's invocation always occurs on the dispatcher where collect(...) was called
 *
 * ```
 *      mviFeature.collect { state -> /* consume state */ }
 *      mviFeature.events.collect { event -> vm.handleEvent(...) }
 *      ....
 *      mviFeature.accept(Action.StoreData)
 * ```
 *
 * @param initialState will be used as first state. State will be updated after reducing of the Effect
 * @param bootstrap performs initial setup, i.e. predloading or observing data streams
 * @param actor will return stream of Effects as a response to incoming Action
 * @param reducer will convert Effect to final State
 * @param eventProducer produces on-time event without side-effects based on Effect (or null if no event required)
 * @param name to distinguish different features in logs
 *
 */
open class MviFeature<Action : Any, Effect : Any, State : Any, Event : Any> constructor(
    private val initialState: State,
    private val bootstrap: MviBootstrap<Effect> = MviBootstrap { emptyFlow() },
    private val actor: MviActor<Action, Effect, State> = MviActor { _, _ -> emptyFlow() },
    private val eventProducer: MviEventProducer<Effect, Event> = MviEventProducer { null },
    private val reducer: MviReducer<Effect, State> = MviReducer { _, state -> state },
    private val name: String = "",
) : AbstractFlow<State>(), FlowCollector<Action> {

    open class Factory<Action : Any, Effect : Any, State : Any, Event : Any>(
        private val bootstrap: MviBootstrap<Effect> = MviBootstrap { emptyFlow() },
        private val actor: MviActor<Action, Effect, State> = MviActor { _, _ -> emptyFlow() },
        private val eventProducer: MviEventProducer<Effect, Event> = MviEventProducer { null },
        private val reducer: MviReducer<Effect, State>,
        private val name: String = "",
    ) {
        fun create(initialState: State): MviFeature<Action, Effect, State, Event> {
            return MviFeature(initialState, bootstrap, actor, eventProducer, reducer, name)
        }
    }

    companion object {
        private const val TAG_PREFIX = "Mvi-"
        private const val ACTIONS_BUFFER_SIZE = 5
        private const val EVENTS_BUFFER_SIZE = 20
    }

    private val tag = "$TAG_PREFIX$name"
    private val actionsFlow = MutableSharedFlow<Action>(replay = 0, ACTIONS_BUFFER_SIZE, BufferOverflow.SUSPEND)
    private val eventsFlow = MutableSharedFlow<Event>(replay = 0, EVENTS_BUFFER_SIZE, BufferOverflow.SUSPEND)
    val events: Flow<Event> = eventsFlow.asSharedFlow()

    var state = initialState
        private set

    private val stateFlow: Flow<State> =
        merge(
            observeBootstrap(),
            observeActions()
        )
            .produceEvent()
            .distinctUntilChanged()
            .reduceEffect()
            .onStart { emit(initialState) }
            .distinctUntilChanged()
            .logMviFeature(tag)

    /**
     * Always emits State values within collector's coroutine scope
     */
    override suspend fun collectSafely(collector: FlowCollector<State>) {
        stateFlow.collect(collector::emit)
    }

    override suspend fun emit(value: Action) {
        accept(value)
    }

    suspend fun accept(action: Action) {
        actionsFlow.emit(action)
    }

    private fun observeBootstrap(): Flow<Effect> {
        return bootstrap
            .invoke()
            .onEach { effect -> effect.logValue(tag, "bootstrap") }
    }

    private fun observeActions(): Flow<Effect> {
        return actionsFlow
            .flatMapMerge { action ->
                action.logValue(tag, "action")
                // TODO: not sure about `state` here. If needed - then should be a proper synchronization
                actor
                    .invoke(action, state)
                    .onEach { effect -> effect.logValue(tag, "actor") }
            }
    }

    private fun Flow<Effect>.produceEvent(): Flow<Effect> = onEach { effect ->
        eventProducer
            .invoke(effect)
            ?.also { event ->
                event.logValue(tag, "event")
                eventsFlow.emit(event)
            }
    }

    private fun Flow<Effect>.reduceEffect(): Flow<State> = map { effect ->
        reducer.invoke(effect, state).also { state = it }
    }
}
