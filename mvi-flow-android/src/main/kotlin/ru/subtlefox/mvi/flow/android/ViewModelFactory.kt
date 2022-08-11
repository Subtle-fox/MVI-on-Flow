package ru.subtlefox.mvi.flow.android

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

class ViewModelFactory<out VM : ViewModel>(
    savedStateRegistryOwner: SavedStateRegistryOwner,
    private val create: (stateHandle: SavedStateHandle) -> VM
) : AbstractSavedStateViewModelFactory(savedStateRegistryOwner, null) {

    override fun <VM : ViewModel> create(key: String, modelClass: Class<VM>, handle: SavedStateHandle): VM =
        create.invoke(handle) as VM
}

inline fun <reified T : ViewModel> ComponentActivity.savedStateViewModel(
    noinline create: (stateHandle: SavedStateHandle) -> T
): Lazy<T> = viewModels { ViewModelFactory(this, create) }
