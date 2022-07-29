package ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity


sealed class CountryListEvent {

    data class OpenCurrencyRatesForCountry(
        val countryIso: String
    ) : CountryListEvent()

}
