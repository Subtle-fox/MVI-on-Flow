package ru.subtlefox.mvi.cookbook.screens.sample6.mvi


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import ru.subtlefox.mvi.cookbook.domain.CountriesApi
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateAction
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateEffect
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateState
import ru.subtlefox.mvi.flow.BaseMviActor
import javax.inject.Inject

class SaveStateActor @Inject constructor(
    private val api: CountriesApi
) : BaseMviActor<SaveStateAction, SaveStateEffect, SaveStateState>() {

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

    override fun transformByAction(actionType: Int): Flow<SaveStateAction>.() -> Flow<SaveStateAction> {
        return {
            when (actionType) {
                0 -> debounce(500).distinctUntilChanged().flatMapLatest { flowOf(it) }
                1 -> this
                else -> this
            }
        }
    }

    override fun getActionType(action: SaveStateAction): Int {
        return when (action) {
            is SaveStateAction.FilterChange -> 0
            else -> 1
        }
    }

    override fun transformByAction(
        actionType: Int,
        previousState: SaveStateState
    ): Flow<SaveStateAction>.() -> Flow<SaveStateEffect> {
        return {
            when (actionType) {
                0 -> debounce(1000)
                    .onEach { println("Mvi- after debounce: $it") }
                    .distinctUntilChanged()
                    .flatMapLatest { invoke(it, previousState) }
                    .onEach { println("Mvi- after swithcMap: $it") }

                1 -> flatMapMerge { invoke(it, previousState) }
                else -> flatMapLatest { invoke(it, previousState) }
            }
        }
    }
}
