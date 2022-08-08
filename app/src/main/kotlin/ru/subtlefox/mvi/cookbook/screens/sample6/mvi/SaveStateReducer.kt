package ru.subtlefox.mvi.cookbook.screens.sample6.mvi


import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateEffect
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateState
import ru.subtlefox.mvi.flow.MviReducer

object SaveStateReducer : MviReducer<SaveStateEffect, SaveStateState> {
    override suspend fun invoke(
        effect: SaveStateEffect,
        previousState: SaveStateState
    ): SaveStateState = when (effect) {

        is SaveStateEffect.FilterResult -> {
            previousState.copy(
                items = effect.locales.map {
                    SaveStateState.CountryItem(
                        iso = it.country,
                        name = it.displayCountry,
                        language = it.displayLanguage
                    )
                },
                filter = effect.filter,
                inProgress = false
            )
        }

        is SaveStateEffect.FilterChange -> {
            previousState.copy(
                items = emptyList(),
                filter = effect.filter,
                inProgress = true
            )
        }
    }
}
