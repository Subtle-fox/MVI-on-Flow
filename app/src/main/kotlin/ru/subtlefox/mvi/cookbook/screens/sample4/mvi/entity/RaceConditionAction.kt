package ru.subtlefox.mvi.cookbook.screens.sample4.mvi.entity


sealed class RaceConditionAction {
    object Like : RaceConditionAction() {
        override fun toString() = "Like"
    }

    object Unlike : RaceConditionAction() {
        override fun toString() = "Unlike"
    }

    object Share : RaceConditionAction() {
        override fun toString() = "Share"
    }
}
