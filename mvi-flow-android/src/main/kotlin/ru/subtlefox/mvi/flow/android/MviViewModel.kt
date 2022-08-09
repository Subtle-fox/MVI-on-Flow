package ru.subtlefox.mvi.flow.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ru.subtlefox.mvi.flow.MviFeature

abstract class MviViewModel<Action : Any, State : Any, Event : Any>(
    private val feature: MviFeature<Action, *, State, Event>,
) : ViewModel() {

    private val sharedState by lazy {
        feature.shareIn(viewModelScope, SharingStarted.Lazily, 1)
    }

    open fun accept(action: Action) {
        viewModelScope.launch {
            feature.emit(action)
        }
    }

    suspend fun collectState(collector: FlowCollector<State>) {
        sharedState.collect(collector)
    }

    suspend fun collectEvents(collector: FlowCollector<Event>) {
        feature.events.collect(collector)
    }
}
