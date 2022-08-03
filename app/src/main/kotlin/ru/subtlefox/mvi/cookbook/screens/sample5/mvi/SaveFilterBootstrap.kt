package ru.subtlefox.mvi.cookbook.screens.sample5.mvi


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import ru.subtlefox.mvi.cookbook.domain.CountriesApi
import ru.subtlefox.mvi.cookbook.screens.sample5.data.FilterRepository
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity.SaveFilterEffect
import ru.subtlefox.mvi.flow.MviBootstrap
import javax.inject.Inject

class SaveFilterBootstrap @Inject constructor(
    private val repository: FilterRepository,
    private val api: CountriesApi
) : MviBootstrap<SaveFilterEffect> {

    override fun invoke(): Flow<SaveFilterEffect> {
        return repository
            .filterFlow
            .debounce(1000)
            .distinctUntilChanged()
            .onStart { emit(repository.filterFlow.value) }
            .flatMapLatest(::performFiltration)
            .flowOn(Dispatchers.IO)
    }

    private fun performFiltration(filter: String) = flow {
        emit(SaveFilterEffect.FilterChange(filter))

        val result = api.getCountryList(filter)

        emit(SaveFilterEffect.FilterResult(result))
    }
}
