package ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy

sealed class DynamicCurrencyEvent {
    data class Error(val message: String) : DynamicCurrencyEvent()
}