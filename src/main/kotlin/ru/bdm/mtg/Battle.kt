package ru.bdm.mtg


class Battle(val player: Player, val enemyPlayer: Player) {

    var state = Pair(StatePlayer(player.name), StatePlayer(enemyPlayer.name))
    val me: StatePlayer
        get() = state.me
    val enemy: StatePlayer
        get() = state.enemy


    init {
        me.hand.addAll(List(5) { Land() })
        //state.enemy.hand.addAll(List(5) { Land() })
    }

    fun nextTurn() {
        state = turn(if (player.name == me.name) player else enemyPlayer)
    }

    private fun turn(player: Player): State {
        println(state)
        if (me.phase == Phase.END_ATTACK) {
            return executeAllCards().nextTurn()
        }
        val states = nextStates(activeCards()) + state.clone().nextTurn()
        return player.chooseAction(state, states)
    }

    private fun executeAllCards(): State {
        CardExecutor.executeAll(state, activeCards())
        return state
    }

    private fun activeCards(): List<Card> = (state.me.hand + state.me.battlefield + state.me.lands).toList()


    private fun nextStates(cards: List<Card>): List<State> {
        return CardExecutor.resultStates(state, cards)
    }
}




