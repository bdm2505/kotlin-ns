package ru.bdm.mtg

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("RotateCard")
open class RotateCard(var rotated: Boolean = false) : Card() {

    override fun toString(): String {
        return super.toString() + if (rotated) "-R" else ""
    }

    fun rotate(newRotate: Boolean = true)  {
        rotated = newRotate
    }
}

open class RotateCardExecutor(): Executor() {

}