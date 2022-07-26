package ru.subtlefox.mvi.cookbook.screens.sample2.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.subtlefox.mvi.cookbook.domain.model.Currency
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository @Inject constructor() {
    private val currencyFlow = MutableStateFlow(Currency.Rub)

    val currentCurrency = currencyFlow.asStateFlow()

    suspend fun setCurrency(currency: Currency) {
        currencyFlow.emit(currency)
    }
}