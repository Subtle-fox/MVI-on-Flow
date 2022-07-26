package ru.subtlefox.mvi.cookbook.screens.sample1.entities

sealed class StaticCurrencyState {
    data class Data(
        val srcCurrency: String,
        val srcAmount: String,
        val dstCurrency: String,
        val dstAmount: String,
    ) : StaticCurrencyState()

    object Loading : StaticCurrencyState() {
        override fun toString() = "CurrencyState.Loading"
    }
}
