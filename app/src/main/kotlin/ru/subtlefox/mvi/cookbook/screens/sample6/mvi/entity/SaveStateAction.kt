package ru.subtlefox.mvi.cookbook.screens.sample6.mvi.entity


sealed class SaveStateAction {

    data class FilterChange(
        val filter: String
    ) : SaveStateAction()

    object Stub: SaveStateAction() {
        override fun toString(): String {
            return "Stub"
        }
    }
}
