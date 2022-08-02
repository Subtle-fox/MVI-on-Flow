package ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy

sealed class DynamicCurrencyState {
    data class Data(
        val srcCurrency: String,
        val srcAmount: String,
        val dstCurrency: String,
        val dstAmount: String,
    ) : DynamicCurrencyState()

    object Loading : DynamicCurrencyState() {
        override fun toString() = "CurrencyState.Loading"
    }
}
