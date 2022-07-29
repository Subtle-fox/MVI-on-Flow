package ru.subtlefox.mvi.cookbook.screens.sample3.mvi


import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ru.subtlefox.mvi.cookbook.screens.sample3.domain.CountriesApi
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListEffect
import ru.subtlefox.mvi.flow.MviBootstrap
import javax.inject.Inject

class CountryListBootstrap @Inject constructor(
    private val countriesApi: CountriesApi
) : MviBootstrap<CountryListEffect> {

    override fun invoke() = flow<CountryListEffect> {
        val locales = countriesApi.getCountryList(page = 0, CountryListActor.PAGE_SIZE)
        emit(CountryListEffect.PageData(locales, page = 0))
    }.catch {
        emit(CountryListEffect.Error(0))
    }
}