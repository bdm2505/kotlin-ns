package ru.bdm.mtg

import kotlinx.serialization.Serializable
import ru.bdm.mtg.Phase.*
import kotlin.reflect.KClass

@Serializable
data class BattleState(var phase: Phase, val me: StatePlayer = StatePlayer(), val enemy: StatePlayer = StatePlayer()) {

    fun nextBattleState(): BattleState {
        phase = phase.next()
        return this
    }

    fun swap(): BattleState = BattleState(phase, enemy, me)
    fun nextTurn(): BattleState {
        return when (phase) {
            START -> nextBattleState().swap()
            BLOCK -> nextBattleState().swap()
            END_ATTACK -> nextBattleState()
            END -> {
                nextBattleState().clone().swap().apply {
                    enemy.apply {
                        mana.clear()
                        numberCourse += 1
                        isLandPlayable = false
                    }
                    endTurn()
                    startTurn()
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

    fun clone(): BattleState = BattleState(phase, me.copy(), enemy.copy())

    fun <T> find(id: Int): T? where T : AbstractCard =
        when {
            me.cards.containsKey(id) -> me.cards[id]!! as T
            enemy.cards.containsKey(id) -> enemy.cards[id]!! as T
            else -> null
        }

    fun <T> find(cl: KClass<T>, id: Int): T? where T : AbstractCard = when {
        me.cards.containsKey(id) -> me.cards[id]!!.let { if (cl.isInstance(it)) (it as T) else null }
        enemy.cards.containsKey(id) -> enemy.cards[id]!!.let { if (cl.isInstance(it)) (it as T) else null }
        else -> null
    }
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


