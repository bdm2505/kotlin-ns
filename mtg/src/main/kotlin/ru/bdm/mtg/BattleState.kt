package ru.bdm.mtg

import kotlinx.serialization.Serializable
import ru.bdm.mtg.Phase.*
import kotlin.reflect.KClass

@Serializable
data class BattleState(var phase: Phase,
                       val me: StatePlayer = StatePlayer(),
                       val enemy: StatePlayer = StatePlayer(),
                       val cards: MutableMap<Int, Card> = mutableMapOf(),
                       var numberCourse: Int = 0,) {

    fun nextBattleState(): BattleState {
        phase = phase.next()
        return this
    }

    fun swap(): BattleState = BattleState(phase, enemy, me, cards, numberCourse)
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



    fun clone(): BattleState = BattleState(phase, me.copy(), enemy.copy(), cards.toMutableMap(), numberCourse)

    operator fun invoke(id:Int): Card = cards[id]!!

    fun isStartPhase(): Boolean = phase == START
    fun isBlockPhase(): Boolean = phase == BLOCK
    fun isEndAttackPhase(): Boolean = phase == END_ATTACK
    fun isEndPhase(): Boolean = phase == END
    fun isActivePhase(): Boolean = isStartPhase() || isEndPhase()

    fun move(
        movedCard: Int,
        start: MutableCollection<Int> = me.hand,
        end: MutableCollection<Int> = me.battlefield,
    ) {
        start -= movedCard
        end += movedCard
    }
}




