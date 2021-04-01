package ru.bdm.mtg

abstract class Player {
    abstract fun chooseAction(current: State, states: List<State>): State
}