package ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy

import ru.subtlefox.mvi.cookbook.domain.model.Currency

sealed class DynamicCurrencyAction {
    data class ChangeCurrency(val oldValue: Currency) : DynamicCurrencyAction()
}