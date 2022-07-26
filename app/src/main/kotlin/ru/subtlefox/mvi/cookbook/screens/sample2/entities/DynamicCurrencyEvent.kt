package ru.subtlefox.mvi.cookbook.screens.sample2.entities

sealed class DynamicCurrencyEvent {
    data class Error(val message: String) : DynamicCurrencyEvent()
}