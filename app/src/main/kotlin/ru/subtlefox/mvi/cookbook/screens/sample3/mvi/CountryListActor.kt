package ru.subtlefox.mvi.cookbook.screens.sample3.mvi


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
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

        internal fun CountriesApi.loadPage(page: Int) = flow {
            emit(CountryListEffect.PageLoading(page))
            val countries = getCountryList(page, PAGE_SIZE)
            emit(CountryListEffect.PageData(countries, page))
        }.catch {
            emit(CountryListEffect.Error(page))
        }
    }

    override fun invoke(action: CountryListAction, previousState: CountryListState): Flow<CountryListEffect> =
        when (action) {

            is CountryListAction.Refresh -> flow {
                emit(CountryListEffect.PageData(emptyList(), page = 0))
                emitAll(countriesApi.loadPage(page = 0))
            }

            is CountryListAction.LoadPage ->
                countriesApi.loadPage(action.page)

            is CountryListAction.OpenCurrencyRatesForCountry ->
                flowOf(CountryListEffect.OpenCurrencyRatesForCountry(action.countryIso))

        }

}