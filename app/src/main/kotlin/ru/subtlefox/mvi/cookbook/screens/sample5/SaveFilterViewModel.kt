package ru.subtlefox.mvi.cookbook.screens.sample5

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ru.subtlefox.mvi.cookbook.screens.sample5.data.FilterRepository
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.SaveFilterFeature
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity.SaveFilterAction
import javax.inject.Inject

@HiltViewModel
class SaveFilterViewModel @Inject constructor(
    private val feature: SaveFilterFeature,
    private val saveStateHandle: SavedStateHandle,
    private val repository: FilterRepository
) : ViewModel() {

    companion object {
        private const val BUNDLE_SEARCH_KEY = "search"
    }

    private val sharedState by lazy { feature.shareIn(viewModelScope, SharingStarted.Lazily, 1) }

    init {
        println("Mvi-ViewModel created: ${hashCode()}")

        repository.setFilter(saveStateHandle.get<String>(BUNDLE_SEARCH_KEY).orEmpty())
    }

    fun accept(action: SaveFilterAction) = viewModelScope.launch {
        if (action is SaveFilterAction.FilterChange) saveStateHandle[BUNDLE_SEARCH_KEY] = action.filter
        feature.accept(action)
    }

    fun collectState() = sharedState
}
