package ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy

import ru.subtlefox.mvi.cookbook.domain.model.Currency
import java.math.BigDecimal


sealed class DynamicCurrencyEffect {

    data class Data(
        val from: Currency,
        val to: Currency,
        val rate: BigDecimal?,
    ) : DynamicCurrencyEffect()

    data class Error(
        val error: Throwable
    ) : DynamicCurrencyEffect()

}
