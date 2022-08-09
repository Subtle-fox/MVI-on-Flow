package ru.subtlefox.mvi.cookbook.screens.sample5

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.subtlefox.mvi.cookbook.screens.sample5.data.FilterRepository
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.SaveFilterFeature
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity.SaveFilterAction
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity.SaveFilterState
import ru.subtlefox.mvi.flow.android.MviViewModel
import javax.inject.Inject

@HiltViewModel
class SaveFilterViewModel @Inject constructor(
    private val saveStateHandle: SavedStateHandle,
    feature: SaveFilterFeature,
    repository: FilterRepository
) : MviViewModel<SaveFilterAction, SaveFilterState, Any>(feature) {

    companion object {
        private const val BUNDLE_SEARCH_KEY = "search"
    }

    init {
        println("Mvi-ViewModel created: ${hashCode()}")

        repository.setFilter(saveStateHandle.get<String>(BUNDLE_SEARCH_KEY).orEmpty())
    }

    override fun accept(action: SaveFilterAction) {
        if (action is SaveFilterAction.FilterChange) {
            saveStateHandle[BUNDLE_SEARCH_KEY] = action.filter
        }
        super.accept(action)
    }
}
