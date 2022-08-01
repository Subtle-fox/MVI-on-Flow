package ru.subtlefox.mvi.cookbook.screens.sample3.mvi


import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListAction
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListEffect
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListEvent
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListState
import ru.subtlefox.mvi.flow.MviFeature
import javax.inject.Inject

class CurrencyListFeature @Inject constructor(
    bootstrap: CountryListBootstrap,
    actor: CountryListActor,
) : MviFeature<CountryListAction, CountryListEffect, CountryListState, CountryListEvent>(
    initialState = CountryListState.INITIAL,
    bootstrap = bootstrap,
    actor = actor,
    eventProducer = CountryListEventProducer,
    reducer = CountryListReducer,
    tagPostfix = "CountryList"
)