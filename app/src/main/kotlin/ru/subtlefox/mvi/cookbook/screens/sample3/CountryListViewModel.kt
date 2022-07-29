package ru.subtlefox.mvi.cookbook.screens.sample3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListAction
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.CurrencyListFeature
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val feature: CurrencyListFeature
) : ViewModel() {

    fun accept(action: CountryListAction) = viewModelScope.launch {
        feature.accept(action)
    }

    fun collectState() = feature

    fun collectEvents() = feature.events
}