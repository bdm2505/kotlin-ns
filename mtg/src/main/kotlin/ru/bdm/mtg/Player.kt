package ru.bdm.mtg

abstract class Player(val name:String) {
    abstract fun chooseAction(current: BattleState, battleStates: List<BattleState>): BattleState
}