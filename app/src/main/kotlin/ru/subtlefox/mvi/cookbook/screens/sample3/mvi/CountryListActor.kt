package ru.subtlefox.mvi.cookbook.screens.sample3.mvi


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import ru.subtlefox.mvi.cookbook.screens.sample3.domain.CountriesApi
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListAction
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListEffect
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListState
import ru.subtlefox.mvi.flow.MviActor
import javax.inject.Inject

class CountryListActor @Inject constructor(
    private val countriesApi: CountriesApi
) : MviActor<CountryListAction, CountryListEffect, CountryListState> {

    companion object {
        const val PAGE_SIZE = 20
    }

    override fun invoke(action: CountryListAction, previousState: CountryListState): Flow<CountryListEffect> =
        when (action) {

            is CountryListAction.Refresh -> flow {
                emit(CountryListEffect.PageData(emptyList(), page = 0))
                val effect = loadPage(page = 0)
                emit(effect)
            }

            is CountryListAction.LoadPage -> flow {
                emit(CountryListEffect.PageLoading(action.page))
                val effect = loadPage(action.page)
                emit(effect)
            }

            is CountryListAction.OpenCurrencyRatesForCountry ->
                flowOf(CountryListEffect.OpenCurrencyRatesForCountry(action.countryIso))

        }

    private suspend fun loadPage(page: Int): CountryListEffect = try {
        val countries = countriesApi.getCountryList(page, PAGE_SIZE)
        CountryListEffect.PageData(countries, page)
    } catch (_: Exception) {
        CountryListEffect.Error(page)
    }
}