package ru.subtlefox.mvi.cookbook.screens.sample1.mvi

import ru.subtlefox.mvi.cookbook.screens.sample1.mvi.entitiy.StaticCurrencyEffect
import ru.subtlefox.mvi.cookbook.screens.sample1.mvi.entitiy.StaticCurrencyState
import ru.subtlefox.mvi.flow.MviFeature
import javax.inject.Inject

/*
    Feature that doesn't require any actions.

    Define:
     # bootstrap for preload
     # reducer for domain-to-ui state converting
 */
class StaticCurrencyFeature @Inject constructor(
    currencyBootstrap: StaticCurrencyBootstrap,
) : MviFeature<Any, StaticCurrencyEffect, StaticCurrencyState, Any>(
    initialState = StaticCurrencyState.Loading,
    bootstrap = currencyBootstrap,
    reducer = StaticCurrencyReducer,
    name = "Sample[1]"
)
