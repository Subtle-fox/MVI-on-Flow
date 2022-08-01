package ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity

import java.util.Locale


sealed class CountryListEffect {

    data class PageLoading(
        val page: Int,
    ) : CountryListEffect()

    data class PageData(
        val countries: List<Locale>,
        val page: Int,
    ) : CountryListEffect()

    data class OpenCurrencyRatesForCountry(
        val currencyIso: String
    ) : CountryListEffect()

    data class Error(
        val page: Int
    ) : CountryListEffect()

}
