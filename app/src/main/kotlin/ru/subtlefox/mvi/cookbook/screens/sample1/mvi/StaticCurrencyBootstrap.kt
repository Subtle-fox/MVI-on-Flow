package ru.subtlefox.mvi.cookbook.screens.sample1.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.subtlefox.mvi.cookbook.domain.CurrencyRateApi
import ru.subtlefox.mvi.cookbook.domain.model.Currency
import ru.subtlefox.mvi.cookbook.screens.sample1.mvi.entitiy.StaticCurrencyEffect
import ru.subtlefox.mvi.flow.MviBootstrap
import javax.inject.Inject

class StaticCurrencyBootstrap @Inject constructor(
    private val currencyRateApi: CurrencyRateApi
) : MviBootstrap<StaticCurrencyEffect> {

    override fun invoke(): Flow<StaticCurrencyEffect> {
        return flow {
            val result = currencyRateApi.getRate(Currency.Usd, Currency.Rub)
            emit(result)
        }
    }
}