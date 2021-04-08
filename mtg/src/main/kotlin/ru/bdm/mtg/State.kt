package ru.bdm.mtg

import kotlinx.serialization.Serializable
import ru.bdm.mtg.Phase.*

@Serializable
data class State(val me:StatePlayer = StatePlayer(), val enemy: StatePlayer = StatePlayer()){

    fun nextBattleState(): State {
        me.phase = me.phase.next()
        enemy.phase = enemy.phase.next()
        return this
    }
    fun swap(): State = State(enemy, me)
    fun nextTurn(): State {
        return when (me.phase) {
            START -> nextBattleState()
            ATTACK -> nextBattleState().swap()
            BLOCK -> nextBattleState().swap()
            END_ATTACK -> nextBattleState()
            END -> {
                val res = nextBattleState().clone()
                res.me.mana.clear()
                res.me.numberCourse += 1
                res.me.isLandPlayable = false
                for(card in res.me.activeCards())
                    card.reset()
                res
            }
        }
    }
    fun getDifference(next: State): Pair<Difference, Difference> {
        return Pair(me.getDifference(next.me), enemy.getDifference(next.enemy))
    }

    fun updateCard(card: AbstractCard) {
        me.updateCard(card)
        enemy.updateCard(card)
    }

    fun clone(): State = State(me.copy(), enemy.copy())
}


