package ru.bdm.mtg

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface LandInterface : RotateCardInterface {

}

class LandExecutor : Executor(), LandInterface {


    init {
        val land = card as Land
        one({ canPlay() && !me.isLandPlayable }, {
            move(me.hand, me.lands)
            me.isLandPlayable = true
        })

        one({ inLands() && !land.rotated }, {
            addMana(land.color)
            rotate()
        })
    }

}

@Serializable
@SerialName("Land")
open class Land() : RotateCard() {
    var color: Mana = Mana.RED

    constructor(color: Mana, rotate: Boolean = false) : this() {
        this.color = color
        this.rotated = rotate
    }


    override fun toString(): String {
        return super.toString() + "-$color"
    }

    override fun eq(card: Any?): Boolean {
        if (this === card) return true
        if (card !is Land) return false
        if (!super.equals(card)) return false

        if (color != card.color) return false

        return true
    }


}