package ru.subtlefox.mvi.cookbook.screens.sample2.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.subtlefox.mvi.cookbook.domain.model.Currency
import ru.subtlefox.mvi.cookbook.screens.sample2.data.CurrencyRepository
import ru.subtlefox.mvi.cookbook.screens.sample2.entities.DynamicCurrencyAction
import ru.subtlefox.mvi.cookbook.screens.sample2.entities.DynamicCurrencyEffect
import ru.subtlefox.mvi.cookbook.screens.sample2.entities.DynamicCurrencyState
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

        val newValue = Currency.values().let { array ->
            val currentIndex = array.indexOfFirst { it == action.oldValue }
            array.getOrNull(currentIndex + 1) ?: array.first()
        }

        repository.setCurrency(newValue)
    }
}