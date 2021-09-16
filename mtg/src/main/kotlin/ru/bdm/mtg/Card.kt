package ru.bdm.mtg

import kotlinx.serialization.Serializable

object NextId : () -> Int {
    var currId = 0
    override fun invoke(): Int {
        return currId++
    }
}


@Serializable
open class Card(var id: Int = NextId()) : Copied, Cloneable {

    var cost: Kit<Mana> = emptyKit()
    var tags: MutableSet<Tag> = mutableSetOf()
    var status: MutableSet<Status> = mutableSetOf(Status.EMPTY)
    var buffs: MutableList<Buff> = mutableListOf()

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
        tags = HashSet(card.tags)
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


    open infix fun eq(card: Any?): Boolean {
        if (this === card) return true
        if (card !is Card) return false

        if (id != card.id) return false
        if (cost != card.cost) return false
        if (tags != card.tags) return false
        if (status != card.status) return false

        return true
    }

    fun isFlying() = status.contains(Status.FLYING)



    fun canPlay(state: BattleState) = state.me.inHand(id) && enoughMana(state.me.mana) && (state.isStartPhase() || state.isEndPhase())

    fun enoughMana(playerMana: Kit<Mana>): Boolean {
        for (mana in cost) {
            if (
                mana.key != Mana.NEUTRAL &&
                (!playerMana.containsKey(mana.key) || playerMana[mana.key]!! < mana.value)
            )
                return false
        }
        return cost.count() <= playerMana.count()
    }

    fun addMana(state: BattleState, color: Mana) {
        state.me.mana.add(color)
    }




    fun spendMana(player: StatePlayer) {
        println("spend mana ${player.mana} - $cost ")
        for (m in cost) {
            player.mana[m.key]?.let {
                if (it > m.value)
                    player.mana[m.key] = it - m.value
                else
                    player.mana.remove(m.key)
            }
        }
        cost[Mana.NEUTRAL]?.let {
            var count = it
            for (m in player.mana) {
                if (m.value > count) {
                    player.mana[m.key] = m.value - count
                    break
                }
                if (m.value == count) {
                    player.mana.remove(m.key)
                    break
                }
                if (m.value < count) {
                    count -= m.value
                    player.mana.remove(m.key)
                }
            }
        }
        println("end spend mana ${player.mana}")
    }
}