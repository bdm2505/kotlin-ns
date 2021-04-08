package ru.bdm.mtg

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
            conditionRotate()
            addMana(land.color)
            rotate(me.lands)
            conditionRotate()
        }
    }
}

open class Land(var color: Mana = Mana.RED, rotated: Boolean = false) : RotateCard(rotated) {

    override fun toString(): String {
        return super.toString() + "-$color"
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + color.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Land) return false
        if (!super.equals(other)) return false

        if (color != other.color) return false

        return true
    }

}