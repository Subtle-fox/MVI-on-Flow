package ru.subtlefox.mvi.cookbook.screens.sample3.mvi


import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListEffect
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListState
import ru.subtlefox.mvi.flow.MviReducer
import java.util.Locale

object CountryListReducer : MviReducer<CountryListEffect, CountryListState> {

    override suspend fun invoke(effect: CountryListEffect, previousState: CountryListState): CountryListState =
        when (effect) {

            is CountryListEffect.PageData -> {
                effect.mapToState(previousState)
            }

            is CountryListEffect.PageLoading -> {
                previousState.copy(
                    items = previousState.items.dropLoadersAndErrors() + CountryListState.LoadingItem,
                    nextPage = null
                )
            }

            is CountryListEffect.Error -> {
                previousState.copy(
                    items = previousState.items.dropLoadersAndErrors() + CountryListState.ErrorItem(effect.page),
                    nextPage = null
                )
            }

            else -> previousState
        }


    private fun CountryListEffect.PageData.mapToState(previousState: CountryListState): CountryListState {
        return if (page == 0) {
            CountryListState(
                countries.mapToStateItems(),
                lastPage = page,
                nextPage = page.inc()
            )
        } else if (page - previousState.lastPage == 1) {
            val newItems = previousState.items.dropLoadersAndErrors() + countries.mapToStateItems()
            previousState.copy(
                items = newItems,
                lastPage = page,
                nextPage = page.inc()
            )
        } else {
            // This is a stale data, just ignore
            previousState
        }
    }

    private fun List<Locale>.mapToStateItems(): List<CountryListState.CountryItem> = map {
        CountryListState.CountryItem(
            iso = it.country,
            name = it.displayCountry,
            language = it.displayLanguage
        )
    }

    private fun List<Any>.dropLoadersAndErrors(): List<Any> {
        return toMutableList()
            .apply { removeIf { it is CountryListState.LoadingItem || it is CountryListState.ErrorItem } }
            .toList()
    }
}

