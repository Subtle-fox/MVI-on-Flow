package ru.subtlefox.mvi.cookbook.screens.sample2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.subtlefox.mvi.cookbook.screens.sample2.entities.DynamicCurrencyAction
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.DynamicCurrencyFeature
import javax.inject.Inject

@HiltViewModel
class DynamicCurrencyViewModel @Inject constructor(
    private val feature: DynamicCurrencyFeature
) : ViewModel() {

    fun accept(action: DynamicCurrencyAction) = viewModelScope.launch {
        feature.accept(action)
    }

    fun collectState() = feature

    fun collectEvents() = feature.events
}