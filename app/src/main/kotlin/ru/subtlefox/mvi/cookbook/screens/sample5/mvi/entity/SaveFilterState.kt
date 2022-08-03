package ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity

data class SaveFilterState(
    val items: List<CountryItem>,
    val filter: String,
    val inProgress: Boolean,
) {

    companion object {
        val INITIAL = SaveFilterState(
            items = emptyList(),
            filter = "",
            inProgress = false
        )
    }

    data class CountryItem(
        val iso: String,
        val name: String,
        val language: String,
    )
}
