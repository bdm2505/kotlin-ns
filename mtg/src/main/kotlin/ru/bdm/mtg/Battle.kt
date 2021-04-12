package ru.bdm.mtg

import ru.bdm.mtg.cards.creatures.Creature


class Battle(val player: Player = ZeroPlayer("zero1"), val enemyPlayer: Player = ZeroPlayer("zero2")) {


    var state: BattleState = BattleState(StatePlayer(player.name), StatePlayer(enemyPlayer.name))
    val me: StatePlayer
        get() = state.me
    val enemy: StatePlayer
        get() = state.enemy

    fun start() {
        state.startTurn()
    }

    fun nextTurn() {
        state = turn(if (player.name == me.name) player else enemyPlayer)
    }

    private fun turn(player: Player): BattleState {

        if (me.phase == Phase.END_ATTACK) {
            return executeAllCards().nextTurn()
        }
        val states = nextStates(me.activeCards()) + state.clone().endAction().nextTurn()
        return player.chooseAction(state, states)
    }

    private fun executeAllCards(): BattleState {
        CardExecutor.executeAll(state, me.activeCards().toList())
        return state
    }


    fun isEnd(): Boolean = me.hp <= 0 || enemy.hp <= 0 || me.deck.isEmpty() || enemy.deck.isEmpty()

    fun nextStates(cards: List<AbstractCard>): List<BattleState> {
        return CardExecutor.resultStates(state, cards.toList())
    }
}

fun main() {
    val battle = Battle(SocketPlayer("first", 24009), ZeroPlayer("second")).apply {
        me.addAll(List(4) { Creature(3, 4) })
        enemy.addAll(List(4) { Creature(4, 2) })
    }
    battle.start()
    println("started..")
    while (!battle.isEnd()) {
        battle.nextTurn()
    }
}


