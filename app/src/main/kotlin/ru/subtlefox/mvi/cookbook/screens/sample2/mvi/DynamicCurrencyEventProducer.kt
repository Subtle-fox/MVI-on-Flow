package ru.subtlefox.mvi.cookbook.screens.sample2.mvi

import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy.DynamicCurrencyEffect
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy.DynamicCurrencyEvent
import ru.subtlefox.mvi.flow.MviEventProducer

object DynamicCurrencyEventProducer : MviEventProducer<DynamicCurrencyEffect, DynamicCurrencyEvent> {

    override fun invoke(effect: DynamicCurrencyEffect) = when (effect) {
        is DynamicCurrencyEffect.Error -> DynamicCurrencyEvent.Error(effect.error.message.orEmpty())
        else -> null
    }

}