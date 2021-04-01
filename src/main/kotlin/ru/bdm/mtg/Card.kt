package ru.bdm.mtg



typealias Actions = MutableList<Pair<() -> Boolean, () -> Unit>>
open class Card : Copied, Cloneable {

    var cost: MutableMap<Char, Int> = mutableMapOf()

    lateinit var me: StatePlayer
    lateinit var enemy: StatePlayer

    var actions: Actions = mutableListOf()

    fun act(condition: () -> Boolean, reaction: () -> Unit){
        actions.add(Pair(condition, reaction))
    }

    fun setStates(me:StatePlayer, enemy: StatePlayer){
        this.me = me
        this.enemy = enemy
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
        return clone() as Card
    }

}