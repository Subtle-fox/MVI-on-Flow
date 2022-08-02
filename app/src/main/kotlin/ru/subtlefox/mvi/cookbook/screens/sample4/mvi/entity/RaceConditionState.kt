package ru.subtlefox.mvi.cookbook.screens.sample4.mvi.entity


data class RaceConditionState(
    val likeAmount: String,
    val likeShare: String,
    val progress: String?
) {
    companion object {
        val INITIAL = RaceConditionState("0", "", null)
    }
}
