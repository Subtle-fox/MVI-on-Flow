package ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity


data class CountryListState(
    val items: List<Any>,
    val lastPage: Int,
    val nextPage: Int?
) {
    companion object {
        val INITIAL = CountryListState(
            items = listOf(),
            lastPage = 0,
            nextPage = null
        )
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
