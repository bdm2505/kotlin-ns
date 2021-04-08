package ru.bdm.mtg

object NextId : () -> Int {
    var currId = 0
    override fun invoke(): Int {
        return currId++
    }
}

open class Card(val id: Int = NextId()) : Copied, Cloneable {

    var cost: Kit<Mana> = emptyKit()
    var tags: MutableList<Tag> = mutableListOf()
    var status: MutableSet<Status> = mutableSetOf(Status.EMPTY)


    fun tag(vararg tag: Tag) {
        tags.addAll(tag)
    }

    fun status(vararg st: Status) {
        status.addAll(st)
    }

    fun cost(s: String) {
        cost = s.toCost()
    }

    val name: String
        get() = javaClass.simpleName

    override fun toString(): String {
        return javaClass.simpleName + "-$id"
    }


    override fun copy(): Card {
        val card = (clone() as Card)
        card.copyDataFrom(this)
        return card
    }

    open fun copyDataFrom(card: Card) {
        cost = HashMap(card.cost)
        tags = ArrayList(card.tags)
        status = HashSet(card.status)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Card) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }


}