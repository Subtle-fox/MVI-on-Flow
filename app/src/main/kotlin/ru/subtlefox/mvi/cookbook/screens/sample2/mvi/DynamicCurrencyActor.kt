package ru.subtlefox.mvi.cookbook.screens.sample2.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.subtlefox.mvi.cookbook.domain.model.Currency
import ru.subtlefox.mvi.cookbook.screens.sample2.data.CurrencyRepository
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy.DynamicCurrencyAction
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy.DynamicCurrencyEffect
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy.DynamicCurrencyState
import ru.subtlefox.mvi.flow.MviActor
import javax.inject.Inject

class DynamicCurrencyActor @Inject constructor(
    private val repository: CurrencyRepository
) : MviActor<DynamicCurrencyAction, DynamicCurrencyEffect, DynamicCurrencyState> {

    override fun invoke(
        action: DynamicCurrencyAction,
        previousState: DynamicCurrencyState
    ): Flow<DynamicCurrencyEffect> {
        return when (action) {
            is DynamicCurrencyAction.ChangeCurrency -> flow {
                changeCurrency(action)
            }
        }
    }

    private suspend fun changeCurrency(action: DynamicCurrencyAction.ChangeCurrency) {
        // Just cycle through currency's values:

        val currentCurrency = repository.currentCurrency.value
        val newValue = Currency.values().let { array ->
            val currentIndex = array.indexOfFirst { it == currentCurrency }
            array.getOrNull(currentIndex + 1) ?: array.first()
        }

        repository.setCurrency(newValue)
    }
}