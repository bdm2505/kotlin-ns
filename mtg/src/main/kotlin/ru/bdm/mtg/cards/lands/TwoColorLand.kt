package ru.bdm.mtg.cards.lands

import ru.bdm.mtg.Executor
import ru.bdm.mtg.Mana


interface TwoColorLandInterface : LandInterface {
    val cland: TwoColorLand
        get() = abstractCard as TwoColorLand

    fun playLandAndRotate() {
        move(me.hand, me.lands)
        me.isLandPlayable = true
        rotate()
    }

    fun addAllMana() = listOf({
        addMana(cland.color)
        rotate()
    }, {
        addMana(cland.colorTwo)
        rotate()
    })

}

open class TwoColorLandExecutor : Executor(), TwoColorLandInterface {

    init {
        one(this::canPlayLand) {
            playLandAndRotate()
        }
        any(this::canRotateLand) {
            addAllMana()
        }
    }
}

open class TwoColorLand(color: Mana, val colorTwo: Mana) : Land(color) {

    override fun executor(): Executor {
        return TwoColorLandExecutor()
    }

    override fun eq(card: Any?): Boolean {
        if (this === card) return true
        if (card !is TwoColorLand) return false
        if (!super.equals(card)) return false

        if (colorTwo != card.colorTwo) return false

        return true
    }

    override fun toString(): String {
        return super.toString() + " or $colorTwo'"
    }
}