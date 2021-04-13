package ru.bdm.mtg.cards.creatures

import ru.bdm.mtg.BattleState
import ru.bdm.mtg.Executor
import ru.bdm.mtg.Tag
import ru.bdm.mtg.toMana

interface SeasonedHallowbladeInterface : CreatureInterface {
    val seas get() = abstractCard as SeasonedHallowblade

    fun canRotate() = isActivePhase() && inBattlefield() && me.hand.size >= 1
    fun rotateAndActive() = me.hand.map {
        {
            move(me.hand, me.graveyard, it)
            seas.indestructible = true
        }
    }
}

class SeasonedHallowbladeExecutor : CreatureExecutor(), SeasonedHallowbladeInterface {

    init {
        any(this::canRotate) {
            rotateAndActive()
        }
    }

}

class SeasonedHallowblade(var indestructible: Boolean = false) : Creature(3, 1) {

    override var hp: Int = 1
        set(value) {
            if (!indestructible)
                field = value
        }

    init {
        cost = "CW".toMana()
        tag(Tag.HUMAN, Tag.WARRIOR)
    }

    override fun executor(): Executor = SeasonedHallowbladeExecutor()
    override fun endTurn(state: BattleState) {
        super.endTurn(state)
        indestructible = false
    }
}