package ru.subtlefox.mvi.cookbook.screens.sample3.mvi


import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListEffect
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListState
import ru.subtlefox.mvi.flow.MviReducer
import java.util.Locale
import java.util.UUID

object CountryListReducer : MviReducer<CountryListEffect, CountryListState> {

    override suspend fun invoke(effect: CountryListEffect, previousState: CountryListState): CountryListState =
        when (effect) {

            is CountryListEffect.PageLoading -> {
                if (effect.page - previousState.lastPage <= 1 || true) {
                    previousState.copy(items = previousState.items.dropLoadersAndErrors() + CountryListState.LoadingItem)
                } else {
                    previousState
                }
            }

            is CountryListEffect.PageData -> {
                if (effect.page == 0) {
                    CountryListState(effect.countries.mapToStateItems(), lastPage = 0, UUID.randomUUID())
                } else if (effect.page - previousState.lastPage == 1) {
                    val newItems = previousState.items.dropLoadersAndErrors() + effect.countries.mapToStateItems()
                    previousState.copy(items = newItems, lastPage = effect.page)
                } else {
                    // This is a stale data, just ignore
                    previousState
                }
            }

            is CountryListEffect.Error -> {
                previousState.copy(
                    items = previousState.items.dropLoadersAndErrors() + CountryListState.ErrorItem(effect.page)
                )
            }

            else -> previousState
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

