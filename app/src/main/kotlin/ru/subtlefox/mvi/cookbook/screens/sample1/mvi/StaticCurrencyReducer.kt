package ru.subtlefox.mvi.cookbook.screens.sample1.mvi

import ru.subtlefox.mvi.cookbook.domain.model.Currency
import ru.subtlefox.mvi.cookbook.screens.sample1.mvi.entitiy.StaticCurrencyEffect
import ru.subtlefox.mvi.cookbook.screens.sample1.mvi.entitiy.StaticCurrencyState
import ru.subtlefox.mvi.flow.MviReducer
import java.math.BigDecimal
import java.math.RoundingMode

object StaticCurrencyReducer : MviReducer<StaticCurrencyEffect, StaticCurrencyState> {

    override suspend fun invoke(effect: StaticCurrencyEffect, previousState: StaticCurrencyState): StaticCurrencyState {
        return mapDataEffect(effect)
    }

    private fun mapDataEffect(effect: StaticCurrencyEffect): StaticCurrencyState {
        return with(effect) {
            StaticCurrencyState.Data(
                srcCurrency = getCurrencyName(from),
                dstCurrency = getCurrencyName(to),
                srcAmount = "1",
                dstAmount = getRoundedRate(rate)
            )
        }
    }

    private fun getCurrencyName(currency: Currency) = currency.toString().uppercase()

    private fun getRoundedRate(rate: BigDecimal) = rate.setScale(2, RoundingMode.HALF_EVEN).toPlainString()
}