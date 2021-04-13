package ru.bdm.mtg

import kotlinx.serialization.Serializable
import ru.bdm.mtg.Phase.*

@Serializable
data class BattleState(val me: StatePlayer = StatePlayer(), val enemy: StatePlayer = StatePlayer(), val phase: Phase) {

    fun nextBattleState(): BattleState = BattleState(me, enemy, phase.next())

    fun swap(): BattleState = BattleState(enemy, me, phase)
    fun nextTurn(): BattleState {
        return when (phase) {
            START -> nextBattleState()
            BLOCK -> nextBattleState().swap()
            END_ATTACK -> nextBattleState()
            END -> {
                nextBattleState().clone().swap().apply {
                    enemy.apply {
                        mana.clear()
                        numberCourse += 1
                        isLandPlayable = false
                    }
                    this.endTurn()
                    this.startTurn()
                }
            }
        }
    }

    fun takeCardFromDeck() {
        if (me.deck.isNotEmpty()) {
            val id = me.deck.run {
                removeAt(size - 1)
            }
            me.hand += id
        }
    }

    fun getDifference(next: BattleState): Pair<Difference, Difference> {
        return Pair(me.getDifference(next.me), enemy.getDifference(next.enemy))
    }

    fun updateCard(card: AbstractCard) {
        me.updateCard(card)
        enemy.updateCard(card)
    }

    fun clone(): BattleState = BattleState(me.copy(), enemy.copy(), phase)
}

fun BattleState.endTurn() {
    for (card in me.cards.values)
        card.endTurn(this)

}

fun BattleState.startTurn() {
    takeCardFromDeck()
    for (card in me.cards.values)
        card.startTurn(this)
}

fun BattleState.endAction(): BattleState {
    for (card in me.cards.values)
        card.endAction(this)
    return this
}


