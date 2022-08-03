package ru.subtlefox.mvi.cookbook.screens.sample6.mvi


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.subtlefox.mvi.cookbook.domain.CountriesApi
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateAction
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateEffect
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateState
import ru.subtlefox.mvi.flow.MviActor
import javax.inject.Inject

class SaveStateActor @Inject constructor(
    private val api: CountriesApi
) : MviActor<SaveStateAction, SaveStateEffect, SaveStateState> {

    override fun invoke(
        action: SaveStateAction,
        previousState: SaveStateState
    ): Flow<SaveStateEffect> {
        return when (action) {
            is SaveStateAction.FilterChange ->
                if (action.filter != previousState.filter) performFiltration(action.filter) else emptyFlow()
        }
    }

    private fun performFiltration(filter: String) = flow {
        emit(SaveStateEffect.FilterChange(filter))

        val result = api.getCountryList(filter)

        emit(SaveStateEffect.FilterResult(filter, result))
    }.flowOn(Dispatchers.IO)
}
