package ru.subtlefox.mvi.cookbook.screens.sample5.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilterRepository @Inject constructor() {
    private val _filterFlow = MutableStateFlow("")

    val filterFlow = _filterFlow.asSharedFlow()

    fun setFilter(filter: String) {
        _filterFlow.value = filter
    }
}
