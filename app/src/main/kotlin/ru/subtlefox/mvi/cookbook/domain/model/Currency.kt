package ru.subtlefox.mvi.cookbook.domain.model

import java.math.BigDecimal

enum class Currency(val base: BigDecimal) {
    Rub(BigDecimal(60)),
    Usd(BigDecimal(1.0)),
    Eur(BigDecimal(1.1)),
    Cny(BigDecimal(6.7))
}