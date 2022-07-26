package ru.subtlefox.mvi.cookbook.domain.model

import java.math.BigDecimal

data class CurrencyRate(
    val from: Currency,
    val to: Currency,
    val rate: BigDecimal,
)