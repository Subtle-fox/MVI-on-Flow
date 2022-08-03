package ru.subtlefox.mvi.cookbook.screens.sample5.mvi


import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity.SaveFilterAction
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity.SaveFilterEffect
import ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity.SaveFilterState
import ru.subtlefox.mvi.flow.MviFeature
import javax.inject.Inject

class SaveStateFeature @Inject constructor(
    bootstrap: SaveFilterBootstrap,
    actor: SaveFilterActor,
) : MviFeature<SaveFilterAction, SaveFilterEffect, SaveFilterState, Any>(
    initialState = SaveFilterState.INITIAL,
    bootstrap = bootstrap,
    actor = actor,
    reducer = SaveFilterReducer,
    name = "Sample[5]"
)
