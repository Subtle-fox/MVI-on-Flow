package ru.subtlefox.mvi.cookbook.screens.sample4.mvi


import ru.subtlefox.mvi.cookbook.screens.sample4.mvi.entity.RaceConditionEffect
import ru.subtlefox.mvi.cookbook.screens.sample4.mvi.entity.RaceConditionState
import ru.subtlefox.mvi.flow.MviReducer

object RaceConditionReducer : MviReducer<RaceConditionEffect, RaceConditionState> {
    override suspend fun invoke(
        effect: RaceConditionEffect,
        previousState: RaceConditionState
    ): RaceConditionState = when (effect) {

        is RaceConditionEffect.Loading ->
            previousState.copy(progress = "${effect.percent}%")

        is RaceConditionEffect.Data ->
            previousState.copy(likeAmount = effect.likeAmount.toString(), progress = null)

        is RaceConditionEffect.DisplayShare ->
            previousState.copy(likeShare = effect.shareAmount?.let { "You have $it likes!" } ?: "-=[{~~~}]=-") }
}
