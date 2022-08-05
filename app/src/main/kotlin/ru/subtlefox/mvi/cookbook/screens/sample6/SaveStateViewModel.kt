package ru.subtlefox.mvi.cookbook.screens.sample6

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.SaveStateFeatureFactory
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateAction
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateState
import javax.inject.Inject

@HiltViewModel
class SaveStateViewModel @Inject constructor(
    private val featureFactory: SaveStateFeatureFactory,
    private val saveStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        private const val BUNDLE_STATE_KEY = "state"
    }

    private val feature = featureFactory.create(
        saveStateHandle.get<SaveStateState>(BUNDLE_STATE_KEY) ?: SaveStateState.INITIAL
    )

    private val sharedState by lazy {
        feature
            .shareIn(viewModelScope, SharingStarted.Lazily, 1)
            .onEach { saveStateHandle[BUNDLE_STATE_KEY] = it }
    }

    private val actionsFlow = MutableSharedFlow<SaveStateAction>()

    init {
        println("Mvi-ViewModel created: ${hashCode()}")

        viewModelScope.launch {
            actionsFlow
//                .debounce(1_000)
//                .distinctUntilChanged()
                .collect(feature)
        }
    }

    fun accept(action: SaveStateAction) {
        viewModelScope.launch {
            actionsFlow.emit(action)
        }

        viewModelScope.launch {
            actionsFlow.emit(SaveStateAction.Stub)
        }
    }

    fun collectState() = sharedState
}
