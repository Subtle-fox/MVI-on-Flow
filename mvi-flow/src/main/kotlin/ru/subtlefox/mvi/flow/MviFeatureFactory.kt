package ru.subtlefox.mvi.flow

import kotlinx.coroutines.flow.emptyFlow

/**
 * Factory can be used to customize initialState and / or bootstrap' logic
 */

open class MviFeatureFactory<Action : Any, Effect : Any, State : Any, Event : Any>(
    private val defaultInitialState: State,
    private val bootstrapFactory: MviBootstrap.Factory<Effect>,
    private val actor: MviActor<Action, Effect, State> = MviActor { _, _ -> emptyFlow() },
    private val eventProducer: MviEventProducer<Effect, Event> = MviEventProducer { null },
    private val reducer: MviReducer<Effect, State>,
    val name: String,
) {
    constructor(
        defaultInitialState: State,
        bootstrap: MviBootstrap<Effect> = MviBootstrap { emptyFlow() },
        actor: MviActor<Action, Effect, State> = MviActor { _, _ -> emptyFlow() },
        eventProducer: MviEventProducer<Effect, Event> = MviEventProducer { null },
        reducer: MviReducer<Effect, State>,
        name: String,
    ) : this(
        defaultInitialState,
        MviBootstrap.Factory { bootstrap },
        actor,
        eventProducer,
        reducer,
        name
    )

    constructor(
        defaultInitialState: State,
        actor: MviActor<Action, Effect, State>,
        eventProducer: MviEventProducer<Effect, Event> = MviEventProducer { null },
        reducer: MviReducer<Effect, State>,
        name: String,
    ) : this(
        defaultInitialState,
        MviBootstrap { emptyFlow() },
        actor,
        eventProducer,
        reducer,
        name
    )

    /**
     * TODO: contract: initial actions should be empty when bootstrap / bootstrap factory is set
     */
    fun create(initialState: State?): MviFeature<Action, Effect, State, Event> =
        MviFeature(
            initialState = initialState ?: defaultInitialState,
            initialActions = emptyList(),
            bootstrap = bootstrapFactory.create(isRestored = initialState != null),
            actor = actor,
            eventProducer = eventProducer,
            reducer = reducer,
            name = name,
        )

    /**
     * TODO:
     * contract: bootstrap shouldn't be set with initial actions
     */
    fun create(initialState: State?, initialActions: List<Action>): MviFeature<Action, Effect, State, Event> =
        MviFeature(
            initialState = initialState ?: defaultInitialState,
            initialActions = initialActions,
            bootstrap = { emptyFlow() },
            actor = actor,
            eventProducer = eventProducer,
            reducer = reducer,
            name = name
        )
}
