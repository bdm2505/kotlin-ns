package ru.bdm.mtg

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


interface RotateCardInterface : CardInterface {
    fun play() {
        move()
        spendMana()
    }

    fun rotate(newRotate: Boolean = true)  {
        (abstractCard as RotateCard).rotated = newRotate
    }
}

@Serializable
@SerialName("RotateCard")
open class RotateCard() : Card() {
    var rotated: Boolean = false
    constructor(rotate: Boolean) : this(){
        rotated = rotate
    }

    override fun toString(): String {
        return super.toString() + if (rotated) "-R" else ""
    }


    override fun reset() {
        super.reset()
        rotated = false
    }
}