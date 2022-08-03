package ru.subtlefox.mvi.cookbook.screens.sample6.mvi


import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateAction
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateEffect
import ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity.SaveStateState
import ru.subtlefox.mvi.flow.MviFeature
import javax.inject.Inject


class SaveStateFeatureFactory @Inject constructor(
    actor: SaveStateActor
) : MviFeature.Factory<SaveStateAction, SaveStateEffect, SaveStateState, Any>(
    actor = actor,
    reducer = SaveStateReducer,
    name = "Sample[6]",
)
