package ru.subtlefox.mvi.cookbook.screens.sample5

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ru.subtlefox.mvi.cookbook.screens.sample5.data.FilterRepository
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.SaveStateFeature
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity.SaveFilterAction
import javax.inject.Inject

@HiltViewModel
class SaveFilterViewModel @Inject constructor(
    private val feature: SaveStateFeature,
    private val saveStateHandle: SavedStateHandle,
    private val repository: FilterRepository
) : ViewModel() {

    companion object {
        private const val BUNDLE_SEARCH_KEY = "search"
    }

    private val actionsFlow = MutableSharedFlow<SaveFilterAction>()
    private val sharedState by lazy { feature.shareIn(viewModelScope, SharingStarted.Lazily, 1) }

    init {
        println("Mvi-ViewModel created: ${hashCode()}")

        repository.setFilter(saveStateHandle.get<String>(BUNDLE_SEARCH_KEY).orEmpty())

        viewModelScope.launch {
            actionsFlow
                .processActions()
                .collect(feature)
        }
    }

    private fun Flow<SaveFilterAction>.processActions(): Flow<SaveFilterAction> {
        return this
            .debounce(1_000)
            .distinctUntilChanged()
            .onEach { if (it is SaveFilterAction.FilterChange) saveStateHandle[BUNDLE_SEARCH_KEY] = it.filter }
    }

    fun accept(action: SaveFilterAction) = viewModelScope.launch { actionsFlow.emit(action) }

    fun collectState() = sharedState
}
