package ru.bdm.mtg


interface RotateCardInterface : CardInterface {
    fun play(card: RotateCard) {
        rotate()
        spendMana()
        move()
    }

    fun rotate(place: MutableSet<Card> = me.battlefield, newRotate: Boolean = true) {
        place -= card
        (card as RotateCard).rotated = newRotate
        place += card
    }
}

open class RotateCard(var rotated: Boolean = false) : Card() {

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



}