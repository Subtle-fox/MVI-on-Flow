package ru.subtlefox.mvi.cookbook.screens.sample3.mvi


import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListEffect
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListEvent
import ru.subtlefox.mvi.flow.MviEventProducer

object CountryListEventProducer : MviEventProducer<CountryListEffect, CountryListEvent> {

    override fun invoke(effect: CountryListEffect) = when (effect) {

        is CountryListEffect.OpenCurrencyRatesForCountry -> {
            CountryListEvent.OpenCurrencyRatesForCountry(effect.currencyIso)
        }

        else -> null
    }
}
