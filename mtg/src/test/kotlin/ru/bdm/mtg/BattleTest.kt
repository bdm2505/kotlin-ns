package ru.bdm.mtg

import org.junit.jupiter.api.Test

internal class BattleTest{
    class EmptyPlayer(s: String) : Player(s) {

        override fun chooseAction(current: State, states: List<State>): State {
            return states.first()
        }

    }
    @Test
    fun testExecuteAllCards(){
        val battle = Battle(EmptyPlayer("one"), EmptyPlayer("two"))
        battle.nextTurn()

    }
}