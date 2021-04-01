package ru.bdm.mtg

open class RotateCard(var rotated: Boolean = false) : Card() {

    override fun toString(): String {
        return super.toString() + if(rotated) " R" else ""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RotateCard

        if (rotated != other.rotated) return false

        return true
    }

    override fun hashCode(): Int {
        return rotated.hashCode() + toString().hashCode()
    }

}