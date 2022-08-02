package ru.subtlefox.mvi.cookbook.screens.sample5.mvi


import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity.SaveFilterEffect
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity.SaveFilterState
import ru.subtlefox.mvi.flow.MviReducer

object SaveFilterReducer : MviReducer<SaveFilterEffect, SaveFilterState> {
    override suspend fun invoke(
        effect: SaveFilterEffect,
        previousState: SaveFilterState
    ): SaveFilterState = when (effect) {

        is SaveFilterEffect.FilterResult -> {
            previousState.copy(
                items = effect.locales.map {
                    SaveFilterState.CountryItem(
                        iso = it.country,
                        name = it.displayCountry,
                        language = it.displayLanguage
                    )
                },
                inProgress = false
            )
        }

        is SaveFilterEffect.FilterChange -> {
            previousState.copy(
                items = emptyList(),
                filter = effect.filter,
                inProgress = true
            )
        }
    }
}
