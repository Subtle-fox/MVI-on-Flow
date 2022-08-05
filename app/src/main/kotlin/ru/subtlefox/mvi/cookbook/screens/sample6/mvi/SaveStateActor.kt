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
import kotlinx.coroutines.flow.switchMap
import ru.subtlefox.mvi.cookbook.domain.CountriesApi
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateAction
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateEffect
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateState
import ru.subtlefox.mvi.flow.BaseMviActor
import javax.inject.Inject
import kotlin.reflect.KClass

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

        val result = api.getCountryList(filter)

        emit(SaveStateEffect.FilterResult(filter, result))
    }.flowOn(Dispatchers.IO)

    override fun transformByAction(actionType: Int): Flow<SaveStateAction>.() -> Flow<SaveStateAction> {
        return {
            when (actionType) {
                0 -> debounce(1000).onEach { "Mvi-Sample[6] ==> debounce" }
                1 -> this
                else -> this //flatMapLatest { flowOf(it) }
            }
        }
    }

    override fun getActionType(action: SaveStateAction): Int {
        return  when(action) {
            is SaveStateAction.FilterChange -> 0
            is SaveStateAction.Stub -> 1
        }
    }
}
