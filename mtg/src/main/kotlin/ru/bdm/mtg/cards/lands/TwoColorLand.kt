package ru.bdm.mtg.cards.lands

import ru.bdm.mtg.Executor
import ru.bdm.mtg.Mana
import ru.bdm.mtg.RotateCard

interface TwoColorLandInterface : LandInterface {
    val cland: TwoColorLand
        get() = abstractCard as TwoColorLand

    fun addAllMana() = listOf({
            addMana(cland.first)
            rotate()
        }, {
            addMana(cland.second)
            rotate()
        })

}

class TwoColorLandExecutor : Executor(), TwoColorLandInterface {

    init {
        one(this::canPlayLand) {
            move(me.hand, me.lands)
            me.isLandPlayable = true
            rotate()
        }
        any(this::canRotateLand) {
            addAllMana()
        }
    }
}

class TwoColorLand(val first: Mana, val second: Mana) : RotateCard() {

    override fun eq(card: Any?): Boolean {
        if (this === card) return true
        if (card !is TwoColorLand) return false
        if (!super.equals(card)) return false

        if (first != card.first) return false
        if (second != card.second) return false

        return true
    }

    override fun toString(): String {
        return super.toString() + "'$first or $second'"
    }
}