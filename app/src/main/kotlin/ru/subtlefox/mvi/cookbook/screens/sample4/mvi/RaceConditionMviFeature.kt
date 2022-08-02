package ru.subtlefox.mvi.cookbook.screens.sample4.mvi


import ru.subtlefox.mvi.cookbook.screens.sample4.mvi.entity.RaceConditionAction
import ru.subtlefox.mvi.cookbook.screens.sample4.mvi.entity.RaceConditionEffect
import ru.subtlefox.mvi.cookbook.screens.sample4.mvi.entity.RaceConditionState
import ru.subtlefox.mvi.flow.MviFeature
import javax.inject.Inject

class RaceConditionFeature @Inject constructor(
    bootstrap: RaceConditionBootstrap,
    actor: RaceConditionActor,
) : MviFeature<RaceConditionAction, RaceConditionEffect, RaceConditionState, Any>(
    initialState = RaceConditionState.INITIAL,
    bootstrap = bootstrap,
    actor = actor,
    reducer = RaceConditionReducer,
    tagPostfix = "RaceCondition"
)