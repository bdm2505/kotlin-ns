package ru.bdm.mtg

import kotlinx.serialization.Serializable
import ru.bdm.mtg.Phase.*

@Serializable
data class BattleState(val me:StatePlayer = StatePlayer(), val enemy: StatePlayer = StatePlayer()){

    fun nextBattleState(): BattleState {
        me.phase = me.phase.next()
        enemy.phase = enemy.phase.next()
        return this
    }
    fun swap(): BattleState = BattleState(enemy, me)
    fun nextTurn(): BattleState {
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
                res.swap()
            }
        }
    }
    fun getDifference(next: BattleState): Pair<Difference, Difference> {
        return Pair(me.getDifference(next.me), enemy.getDifference(next.enemy))
    }

    fun updateCard(card: AbstractCard) {
        me.updateCard(card)
        enemy.updateCard(card)
    }

    fun clone(): BattleState = BattleState(me.copy(), enemy.copy())
}


