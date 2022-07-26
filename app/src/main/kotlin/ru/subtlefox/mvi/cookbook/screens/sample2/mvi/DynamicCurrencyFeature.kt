package ru.subtlefox.mvi.cookbook.screens.sample2.mvi

import ru.subtlefox.mvi.cookbook.screens.sample2.entities.DynamicCurrencyAction
import ru.subtlefox.mvi.cookbook.screens.sample2.entities.DynamicCurrencyEffect
import ru.subtlefox.mvi.cookbook.screens.sample2.entities.DynamicCurrencyEvent
import ru.subtlefox.mvi.cookbook.screens.sample2.entities.DynamicCurrencyState
import ru.subtlefox.mvi.flow.MviFeature
import javax.inject.Inject

/*
    Feature that process some actions.

    Define:
     # bootstrap for preload
     # actor for action processing
     # eventProducer for domain-to-event mapping
     # reducer for domain-to-ui state mapping
 */
class DynamicCurrencyFeature @Inject constructor(
    bootstrap: DynamicCurrencyBootstrap,
    actor: DynamicCurrencyActor
) : MviFeature<DynamicCurrencyAction, DynamicCurrencyEffect, DynamicCurrencyState, DynamicCurrencyEvent>(
    initialState = DynamicCurrencyState.Loading,
    bootstrap = bootstrap,
    actor = actor,
    eventProducer = DynamicCurrencyEventProducer,
    reducer = DynamicCurrencyReducer,
    tagPostfix = "Sample[2]"
)