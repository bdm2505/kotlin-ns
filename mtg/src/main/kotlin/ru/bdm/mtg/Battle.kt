package ru.bdm.mtg

import ru.bdm.mtg.cards.Creature


class Battle(val player: Player, val enemyPlayer: Player) {

    var state: State = State(StatePlayer(player.name), StatePlayer(enemyPlayer.name))
    val me: StatePlayer
        get() = state.me
    val enemy: StatePlayer
        get() = state.enemy


    init {
        me.hand.addAll(List(3) { Creature(3,4) })
        enemy.hand.addAll(List(7) { Creature(2,1) })
        //state.enemy.hand.addAll(List(5) { Land() })
    }

    fun nextTurn() {
        state = turn(if (player.name == me.name) player else enemyPlayer)
    }

    private fun turn(player: Player): State {

        if (me.phase == Phase.END_ATTACK) {
            return executeAllCards().nextTurn()
        }
        val states = nextStates(me.activeCards()) + state.clone().nextTurn()
        return player.chooseAction(state, states)
    }

    private fun executeAllCards(): State {
        CardExecutor.executeAll(state, me.activeCards().toList())
        return state
    }


    fun isEnd():Boolean = me.hp <= 0 || enemy.hp <= 0

    private fun nextStates(cards: Set<AbstractCard>): List<State> {
        return CardExecutor.resultStates(state, cards.toList())
    }
}

fun main(){
    val battle = Battle(SocketPlayer("first", 24009), SocketPlayer("second", 25009))
    println("started..")
    while (true){
        battle.nextTurn()
    }
}


