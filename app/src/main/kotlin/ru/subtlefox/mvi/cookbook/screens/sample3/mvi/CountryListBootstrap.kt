package ru.subtlefox.mvi.cookbook.screens.sample3.mvi


import kotlinx.coroutines.flow.Flow
import ru.subtlefox.mvi.cookbook.domain.CountriesApi
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListEffect
import ru.subtlefox.mvi.flow.MviBootstrap
import javax.inject.Inject

class CountryListBootstrap @Inject constructor(
    private val countriesApi: CountriesApi
) : MviBootstrap<CountryListEffect> {

    override fun invoke(): Flow<CountryListEffect> = with(CountryListActor) {
        countriesApi.loadPage(page = 0)
    }
}