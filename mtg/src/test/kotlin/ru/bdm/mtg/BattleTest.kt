package ru.bdm.mtg

import org.junit.jupiter.api.Test

internal class BattleTest{
    class EmptyPlayer(s: String) : Player(s) {

        override fun chooseAction(current: BattleState, battleStates: List<BattleState>): BattleState {
            return battleStates.first()
        }

    }
    @Test
    fun testExecuteAllCards(){
        val battle = Battle(EmptyPlayer("one"), EmptyPlayer("two"))
        battle.nextTurn()

    }
}