package ru.subtlefox.mvi.cookbook.screens.sample6.mvi


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.subtlefox.mvi.cookbook.domain.CountriesApi
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateAction
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateEffect
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateState
import ru.subtlefox.mvi.flow.GroupMviActor
import javax.inject.Inject

class SaveStateActor @Inject constructor(
    private val api: CountriesApi
) : GroupMviActor<SaveStateAction, SaveStateEffect, SaveStateState>() {

    companion object {
        const val FILTER_GROUP_ID = 100
    }

    override fun invoke(
        action: SaveStateAction,
        previousState: SaveStateState
    ): Flow<SaveStateEffect> {
        return when (action) {
            is SaveStateAction.FilterChange ->
                if (action.filter != previousState.filter) performFiltration(action.filter) else emptyFlow()
            else -> emptyFlow()
        }
    }

    private fun performFiltration(filter: String) = flow {
        emit(SaveStateEffect.FilterChange(filter))

        kotlinx.coroutines.delay(1000)
        val result = api.getCountryList(filter)

        emit(SaveStateEffect.FilterResult(filter, result))
    }.flowOn(Dispatchers.IO)

    /////////

    override fun getGroup(action: SaveStateAction): Int {
        return when (action) {
            is SaveStateAction.FilterChange -> FILTER_GROUP_ID
            else -> NO_OP_GROUP
        }
    }

    override fun transformByAction(
        actionGroup: Int,
        previousState: SaveStateState
    ): Flow<SaveStateAction>.() -> Flow<SaveStateEffect> = {
        when (actionGroup) {
            FILTER_GROUP_ID ->
                this.debounce(1000)
                    .distinctUntilChanged()
                    .flatMapLatest { invoke(it, previousState) }

            else ->
                this.flatMapMerge { invoke(it, previousState) }
        }
    }
}
