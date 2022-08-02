package ru.subtlefox.mvi.cookbook.screens.sample4.mvi.entity


sealed class RaceConditionEffect {
    data class Loading(val percent: Int = 10) : RaceConditionEffect()
    data class Data(val likeAmount: Int) : RaceConditionEffect()
    data class DisplayShare(val shareAmount: Int?) : RaceConditionEffect()
}
