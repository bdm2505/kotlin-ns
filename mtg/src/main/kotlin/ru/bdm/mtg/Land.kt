package ru.bdm.mtg

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface LandInterface : RotateCardInterface {

}
class LandExecutor : Executor(), LandInterface {
    val land: Land
        get() = card as Land

    fun conditionCanPlay():Boolean {
        return canPlay() && !me.isLandPlayable
    }
    fun reactionCanPlay(): List<() -> Unit>{
        return listOf {
            move(me.hand, me.lands)
            me.isLandPlayable = true
        }
    }

    fun conditionRotate():Boolean {
        return inLands() && !land.rotated
    }
    fun reactionRotate(): List<() -> Unit>{
        return listOf {
            addMana(land.color)
            rotate()
        }
    }
}

@Serializable
@SerialName("land")
open class Land() : RotateCard() {
    var color: Mana = Mana.RED

    constructor(color: Mana, rotate: Boolean = false) : this() {
        this.color = color
        this.rotated = rotate
    }


    override fun toString(): String {
        return super.toString() + "-$color"
    }


}