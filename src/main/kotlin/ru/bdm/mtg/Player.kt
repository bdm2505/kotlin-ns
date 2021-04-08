package ru.bdm.mtg

abstract class Player(val name:String) {
    abstract fun chooseAction(current: State, states: List<State>): State
}