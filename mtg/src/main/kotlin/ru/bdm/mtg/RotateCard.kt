package ru.bdm.mtg

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


interface RotateCardInterface : CardInterface {
    fun play() {
        move()
        rotate()
        spendMana()
    }

    fun rotate(newRotate: Boolean = true)  {
        (card as RotateCard).rotated = newRotate
    }
}

@Serializable
@SerialName("rotate-land")
open class RotateCard : Card() {
    var rotated: Boolean = false

    override fun toString(): String {
        return super.toString() + if (rotated) "-R" else ""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RotateCard) return false
        if (!super.equals(other)) return false

        if (rotated != other.rotated) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + rotated.hashCode()
        return result
    }

    override fun reset() {
        super.reset()
        rotated = false
    }
}