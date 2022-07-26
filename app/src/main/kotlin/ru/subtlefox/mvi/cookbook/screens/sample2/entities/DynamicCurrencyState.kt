package ru.subtlefox.mvi.cookbook.screens.sample2.entities

import ru.subtlefox.mvi.cookbook.domain.model.Currency

sealed class DynamicCurrencyState {
    data class Data(
        val srcCurrency: String,
        val srcAmount: String,
        val dstCurrency: String,
        val dstAmount: String,
        val onChangeAction: () -> Currency
    ) : DynamicCurrencyState()

    object Loading : DynamicCurrencyState() {
        override fun toString() = "CurrencyState.Loading"
    }
}
