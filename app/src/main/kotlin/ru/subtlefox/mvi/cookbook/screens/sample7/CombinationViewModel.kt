package ru.subtlefox.mvi.cookbook.screens.sample7

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.DynamicCurrencyFeature
import ru.subtlefox.mvi.cookbook.screens.sample2.mvi.entitiy.DynamicCurrencyAction
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.SaveFilterFeature
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity.SaveFilterAction
import javax.inject.Inject

/*
    ==== DISCLAIMER =====
    Just for demonstration 2 independent MviFeatures can be combined inside of single ViewModel

    In real world this example is much easier to implement using simple fragment stacking

 */
@HiltViewModel
class CombinationViewModel @Inject constructor(
    private val exchangeFeature: DynamicCurrencyFeature,
    private val filterFeature: SaveFilterFeature,
) : ViewModel() {

    private val sharedState by lazy {
        merge(exchangeFeature, filterFeature).shareIn(viewModelScope, SharingStarted.Lazily, 1)
    }

    fun accept(action: Any) = viewModelScope.launch {
        when (action) {
            is DynamicCurrencyAction -> exchangeFeature.accept(action)
            is SaveFilterAction -> filterFeature.accept(action)
        }
    }

    fun collectState(): SharedFlow<Any> = sharedState

    fun collectEvents(): Flow<Any> = merge(
        exchangeFeature.events,
        filterFeature.events
    )
}
