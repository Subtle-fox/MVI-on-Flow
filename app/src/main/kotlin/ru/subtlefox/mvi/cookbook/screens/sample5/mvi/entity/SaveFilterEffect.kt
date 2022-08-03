package ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity

import java.util.Locale


sealed class SaveFilterEffect {

    data class FilterResult(
        val locales: List<Locale>
    ) : SaveFilterEffect()

    data class FilterChange(
        val filter: String
    ) : SaveFilterEffect()

}
