package ru.subtlefox.mvi.cookbook.screens.sample3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.CurrencyListFeature
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListAction
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListEffect
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListEvent
import ru.subtlefox.mvi.cookbook.screens.sample3.mvi.entity.CountryListState
import ru.subtlefox.mvi.flow.android.MviViewModel
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    feature: CurrencyListFeature
) : MviViewModel<CountryListAction, CountryListState, CountryListEvent>(feature) 
