package ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SaveStateState(
    val items: List<CountryItem>,
    val filter: String,
    val inProgress: Boolean,
) : Parcelable {

    companion object {
        val INITIAL = SaveStateState(
            items = emptyList(),
            filter = "",
            inProgress = false
        )
    }

    @Parcelize
    data class CountryItem(
        val iso: String,
        val name: String,
        val language: String,
    ) : Parcelable
}
