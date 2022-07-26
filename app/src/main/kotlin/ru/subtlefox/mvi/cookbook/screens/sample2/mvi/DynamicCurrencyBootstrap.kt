package ru.subtlefox.mvi.cookbook.screens.sample2.mvi

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import ru.subtlefox.mvi.cookbook.domain.CurrencyRateApi
import ru.subtlefox.mvi.cookbook.domain.model.Currency
import ru.subtlefox.mvi.cookbook.screens.sample2.data.CurrencyRepository
import ru.subtlefox.mvi.cookbook.screens.sample2.entities.DynamicCurrencyEffect
import ru.subtlefox.mvi.flow.MviBootstrap
import javax.inject.Inject

class DynamicCurrencyBootstrap @Inject constructor(
    private val currencyRateApi: CurrencyRateApi,
    private val repository: CurrencyRepository,
) : MviBootstrap<DynamicCurrencyEffect> {

    override fun invoke(): Flow<DynamicCurrencyEffect> {
        return repository.currentCurrency
            .flatMapLatest { selectedCurrency ->
                pollCurrencyRate(selectedCurrency).catch {
                    // if BE failed, flow for selected currency will be terminated.
                    emit(DynamicCurrencyEffect.Error(it))
                }
            }
    }

    private fun pollCurrencyRate(currency: Currency): Flow<DynamicCurrencyEffect> = flow {

        // Reset current rate value after changing currency until we get the first value from BE
        val emptyData = DynamicCurrencyEffect.Data(from = Currency.Usd, to = currency, rate = null)
        emit(emptyData)

        // Poll api each second
        while (true) {
            val result = currencyRateApi.getRate(Currency.Usd, currency)

            // populate with fresh currency rate:
            emit(emptyData.copy(rate = result.rate))
            delay(2000)
        }
    }
}