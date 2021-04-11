package ru.bdm.mtg.cards.lands

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.Executor
import ru.bdm.mtg.Mana
import ru.bdm.mtg.RotateCard
import ru.bdm.mtg.RotateCardInterface

interface LandInterface : RotateCardInterface {
    val land: Land
        get() = abstractCard as Land

    fun canPlayLand() = canPlay() && !me.isLandPlayable

    fun canRotateLand() = inLands() && !land.rotated

    fun playLand() {
        move(me.hand, me.lands)
        me.isLandPlayable = true
    }

}

open class LandExecutor : Executor(), LandInterface {


    init {
        one(this::canPlayLand) {
            playLand()
        }

        one(this::canRotateLand) {
            addMana(land.color)
            rotate()
        }
    }

}

@Serializable
@SerialName("Land")
open class Land() : RotateCard() {

    override fun executor(): Executor = LandExecutor()

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