package ru.subtlefox.mvi.cookbook.screens.sample2.mvi

import ru.subtlefox.mvi.cookbook.domain.model.Currency
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy.DynamicCurrencyEffect
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy.DynamicCurrencyState
import ru.subtlefox.mvi.flow.MviReducer
import java.math.BigDecimal
import java.math.RoundingMode

object DynamicCurrencyReducer : MviReducer<DynamicCurrencyEffect, DynamicCurrencyState> {

    override suspend fun invoke(
        effect: DynamicCurrencyEffect,
        previousState: DynamicCurrencyState
    ): DynamicCurrencyState {
        return when (effect) {
            is DynamicCurrencyEffect.Data -> mapDataEffect(effect)
            else -> previousState
        }
    }

    private fun mapDataEffect(effect: DynamicCurrencyEffect.Data): DynamicCurrencyState {
        return with(effect) {
            DynamicCurrencyState.Data(
                srcCurrency = getCurrencyName(from),
                dstCurrency = getCurrencyName(to),
                srcAmount = "1",
                dstAmount = rate?.let(::getRoundedRate).orEmpty(),
                onChangeAction = { to }
            )
        }
    }

    private fun getCurrencyName(currency: Currency) = currency.toString().uppercase()

    private fun getRoundedRate(rate: BigDecimal) = rate.setScale(2, RoundingMode.HALF_EVEN).toPlainString()
}