package ru.subtlefox.mvi.flow.android

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.subtlefox.mvi.flow.MviFeature
import ru.subtlefox.mvi.flow.MviFeatureFactory

abstract class MviSaveStateViewModel<Action : Any, State : Parcelable, Event : Any>(
    private val featureFactory: MviFeatureFactory<Action, *, State, Event>,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        private const val KEY_STATE = "_state"
    }

    private val savedStateKey = featureFactory.name + KEY_STATE

    open fun restoreState(savedStateHandle: SavedStateHandle): State? {
        return savedStateHandle.get<State>(savedStateKey)
    }

    open fun saveState(state: State, savedStateHandle: SavedStateHandle) {
        savedStateHandle.set(savedStateKey, state)
    }

    private val feature by lazy {
        featureFactory.create(restoreState(savedStateHandle))
    }

    val stateFlow by lazy {
        feature
            .onEach { saveState(it, savedStateHandle) }
            .stateIn(viewModelScope, SharingStarted.Lazily, feature.state)
    }

    val eventsFlow by lazy {
        feature.events
    }

    fun accept(action: Action) {
        viewModelScope.launch {
            feature.emit(action)
        }
    }

    suspend fun collectState(collector: FlowCollector<State>) {
        stateFlow.collect(collector)
    }

    suspend fun collectEvents(collector: FlowCollector<Event>) {
        eventsFlow.collect(collector)
    }
}
