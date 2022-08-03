package ru.subtlefox.mvi.cookbook.screens.sample5.mvi.entity


sealed class SaveFilterAction {

    data class FilterChange(
        val filter: String
    ) : SaveFilterAction()

}
