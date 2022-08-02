package ru.subtlefox.mvi.cookbook.screens.sample5.mvi


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.subtlefox.mvi.cookbook.screens.sample5.data.FilterRepository
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity.SaveFilterAction
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity.SaveFilterEffect
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity.SaveFilterState
import ru.subtlefox.mvi.flow.MviActor
import javax.inject.Inject

class SaveFilterActor @Inject constructor(
    private val repository: FilterRepository
) : MviActor<SaveFilterAction, SaveFilterEffect, SaveFilterState> {

    override fun invoke(
        action: SaveFilterAction,
        previousState: SaveFilterState
    ): Flow<SaveFilterEffect> {
        return when (action) {

            is SaveFilterAction.FilterChange -> flow {
                repository.setFilter(action.filter)
            }
        }
    }
}
