package ru.bdm.mtg.cards.creatures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.BattleState
import ru.bdm.mtg.Executor
import ru.bdm.mtg.Tag
import ru.bdm.mtg.toMana

interface SeasonedHallowbladeInterface : CreatureInterface {
    val seas get() = abstractCard as SeasonedHallowblade

    fun canRotate() = isActivePhase() && inBattlefield() && !seas.rotated && !seas.indestructible && me.hand.size >= 1
    fun rotateAndActive() = me.hand.map {
        {
            move(me.hand, me.graveyard, it)
            seas.indestructible = true
            rotate()
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

@Serializable
@SerialName("SeasonedHallowblade")
class SeasonedHallowblade(var indestructible: Boolean = false) : Creature(3, 1) {


    override fun minusHp(damage: Int) {
        if (!indestructible)
            super.minusHp(damage)
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