package ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy

sealed class DynamicCurrencyAction {
    object  ChangeCurrency : DynamicCurrencyAction() {
        override fun toString() = "ChangeCurrency"
    }
}