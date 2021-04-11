package ru.bdm.mtg

import kotlinx.serialization.Serializable

object NextId : () -> Int {
    var currId = 0
    override fun invoke(): Int {
        return currId++
    }
}
@Serializable
abstract class AbstractCard : Copied {
    abstract val id: Int
    abstract fun reset()
    abstract override fun copy(): AbstractCard

    abstract infix fun eq(card: Any?): Boolean

    infix fun notEq(card: Any?): Boolean = !eq(card)

    abstract var cost: Kit<Mana>
    abstract var tags: MutableList<Tag>
    abstract var status: MutableSet<Status>

    abstract fun executor(): Executor

    init {
        if (!CardExecutor.isRegistered(this::class)) {
            println("register executor for ${this::class.simpleName} -> ${executor()::class.simpleName}")
            CardExecutor.register(this::class, executor())
        }
    }
}

@Serializable
open class Card(override val id: Int = NextId()) : AbstractCard(), Cloneable {

    override var cost: Kit<Mana> = emptyKit()
    override var tags: MutableList<Tag> = mutableListOf()
    override var status: MutableSet<Status> = mutableSetOf(Status.EMPTY)

    override fun executor(): Executor = Executor()


    fun tag(vararg tag: Tag) {
        tags.addAll(tag)
    }

    fun status(vararg st: Status) {
        status.addAll(st)
    }

    fun cost(s: String) {
        cost = s.toMana()
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

    override fun reset() {}

    override infix fun eq(card: Any?): Boolean {
        if (this === card) return true
        if (card !is Card) return false

        if (id != card.id) return false
        if (cost != card.cost) return false
        if (tags != card.tags) return false
        if (status != card.status) return false

        return true
    }

}