package ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity


sealed class CountryListAction {

    object Refresh : CountryListAction()

    data class LoadPage(
        val page: Int
    ) : CountryListAction()

    data class OpenCurrencyRatesForCountry(
        val countryIso: String
    ) : CountryListAction()

}
