package ru.subtlefox.mvi.cookbook.screens.sample2

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.DynamicCurrencyFeature
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy.DynamicCurrencyAction
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy.DynamicCurrencyEvent
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy.DynamicCurrencyState
import ru.subtlefox.mvi.flow.android.MviViewModel
import javax.inject.Inject

@HiltViewModel
class DynamicCurrencyViewModel @Inject constructor(
    feature: DynamicCurrencyFeature
) : MviViewModel<DynamicCurrencyAction, DynamicCurrencyState, DynamicCurrencyEvent>(feature)
