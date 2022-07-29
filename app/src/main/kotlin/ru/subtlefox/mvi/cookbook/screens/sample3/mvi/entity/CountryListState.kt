package ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity

import java.util.UUID


data class CountryListState(
    val items: List<Any>,
    val lastPage: Int,
    val sessionId: UUID
) {
    companion object {
        val INITIAL = CountryListState(listOf(), 0, UUID(0, 0))
    }

    data class CountryItem(
        val iso: String,
        val name: String,
        val language: String,
    )

    object LoadingItem

    data class ErrorItem(
        val failedPage: Int
    )
}
