package ru.bdm.mtg

import java.lang.Thread.yield

typealias State = Pair<StatePlayer, StatePlayer>
class Battle (val player : Player, val enemyPlayer : Player){
    var me : StatePlayer = StatePlayer()
    var enemy : StatePlayer = StatePlayer()
    var myMove: Boolean = true

    init {
       me.hand +=  mutableMapOf(Pair(Land(), 5))
       enemy.hand +=  mutableMapOf(Pair(Land(), 5))
    }

    fun nextTurn(){
        if (myMove){
            val next = turn(Pair(me, enemy), player)
            if(me.numberCourse != next.first.numberCourse)
                myMove = false
            me = next.first
            enemy = next.second
        } else {
            val next = turn(Pair(enemy, me), enemyPlayer)
            if(enemy.numberCourse != next.first.numberCourse)
                myMove = true
            enemy = next.first
            me = next.second
        }
    }

    private fun turn(state: State, player: Player): State {
        val states = mutableListOf<State>()

        states.add(state.copy().nextTurn())

        for(card in me.hand.keys){
            executeCard(card, state, states::add)
        }

        return player.chooseAction(Pair(me, enemy), states)
    }
}
fun State.getDifference(next : State): Pair<Difference, Difference> {
    return Pair(first.getDifference(next.first), second.getDifference(next.second))
}
fun State.copy(): State = Pair(first.copy(), second.copy())

fun State.nextTurn(): State {
    first.mana.clear()
    first.numberCourse += 1
    first.isLandPlayable = false
    return this
}

fun executeCard(card: Card, state:State, append: (State) -> Unit) {
    val cond = "condition"
    val reac = "reaction"
    for(method in card.javaClass.methods){
        if(method.name.startsWith(cond)){
            val condition: Boolean = method.invoke(card) as Boolean
            if (condition) {
                val endName = method.name.substring(cond.length, method.name.length)
                val reaction = card.javaClass.getMethod(reac + endName)
                val stateCopy = state.copy()
                card.setState(stateCopy)
                reaction.invoke(card)
                append(stateCopy)
            }
        }
    }
}