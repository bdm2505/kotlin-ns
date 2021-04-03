package ru.bdm.mtg
import ru.bdm.mtg.copy


open class Card : Copied, Cloneable {

    lateinit var me: StatePlayer
    lateinit var enemy: StatePlayer

    var cost: Kit<Mana> = emptyKit()
    var tags: MutableList<Tag> = mutableListOf()
    var status: MutableList<Status> = mutableListOf()

    fun setStates(me:StatePlayer, enemy: StatePlayer){
        this.me = me
        this.enemy = enemy
    }

    fun tag(vararg tag: Tag){
        tags.addAll(tag)
    }
    fun status(vararg st: Status){
        status.addAll(st)
    }
    fun cost(s: String){
        cost = s.toCost()
    }

    override fun toString(): String {
        return javaClass.simpleName
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun copy(): Card {
        return (clone() as Card).apply {
            cost = HashMap(cost)
            tags = ArrayList(tags)
            status = ArrayList(status)
        }
    }

    fun setState(state: State) {
        me = state.first
        enemy = state.second
    }

}