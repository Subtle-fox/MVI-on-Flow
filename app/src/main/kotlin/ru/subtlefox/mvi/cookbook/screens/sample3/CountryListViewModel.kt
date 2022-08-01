package ru.subtlefox.mvi.cookbook.screens.sample3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.CurrencyListFeature
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListAction
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val feature: CurrencyListFeature
) : ViewModel() {

    fun accept(action: CountryListAction) = viewModelScope.launch {
        feature.accept(action)
    }

    private val sharedState by lazy { feature.shareIn(viewModelScope, SharingStarted.Lazily, 1) }

    fun collectState() = sharedState

    fun collectEvents() = feature.events
}