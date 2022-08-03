package ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity

import java.util.Locale


sealed class SaveStateEffect {

    data class FilterResult(
        val filter: String,
        val locales: List<Locale>
    ) : SaveStateEffect()

    data class FilterChange(
        val filter: String
    ) : SaveStateEffect()
}
