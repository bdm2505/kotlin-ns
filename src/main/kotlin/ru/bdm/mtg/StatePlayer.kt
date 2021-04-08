package ru.bdm.mtg

class StatePlayer(
    val name: String = "no_name",
    val mana: Kit<Mana> = emptyKit(),
    val hand: MutableSet<Card> = mutableSetOf(),
    val lands: MutableSet<Card> = mutableSetOf(),
    val battlefield: MutableSet<Card> = mutableSetOf(),
    var numberCourse: Int = 0,
    var isLandPlayable: Boolean = false,
    val graveyard: ArrayDeque<Card> = ArrayDeque(),
    val deck: MutableSet<Card> = mutableSetOf(),
    var phase: Phase = Phase.START,
    var hp: Int = 20,
) : Copied {
    override fun copy(): StatePlayer {
        return StatePlayer(
            name,
            HashMap(mana),
            hand.copy(),
            lands.copy(),
            battlefield.copy(),
            numberCourse,
            isLandPlayable,
            graveyard.copy(),
            deck.copy(),
            phase,
            hp
        )
    }

    fun getDifference(next: StatePlayer): Difference {
        if (name != next.name)
            return Difference(listOf(), listOf(), listOf(), listOf(), listOf(), listOf(), listOf(), listOf(), listOf(), listOf(), next.numberCourse, listOf(next.phase), next.hp, next.name, next.isLandPlayable)
        return Difference(
            getAdd(mana, next.mana),
            getRemoved(mana, next.mana),
            getAdd(hand, next.hand),
            getAdd(lands, next.lands),
            getAdd(deck, next.deck),
            getAdd(graveyard, next.graveyard),
            getRemoved(hand, next.hand),
            getRemoved(lands, next.lands),
            getRemoved(deck, next.deck),
            getRemoved(graveyard, next.graveyard),
            next.numberCourse,
            if(phase == next.phase) listOf() else listOf(next.phase),
            next.hp,
            next.name,
            next.isLandPlayable
        )
    }

    private fun getAdd(curr: List<Card>, next: List<Card>): List<Card> {
        return next.filter { card -> !curr.contains(card) }
    }

    private fun getAdd(curr: Set<Card>, next: Set<Card>): List<Card> {
        return next.filter { card -> !curr.contains(card) }
    }

    private fun <T> getAdd(curr: Kit<T>, next: Kit<T>): List<T> {
        return next.filter { card -> !curr.contains(card.key) || (curr.count(card.key) < next.count(card.key)) }
            .map { it.key }
    }

    private fun getRemoved(curr: List<Card>, next: List<Card>): List<Card> {
        return curr.filter { card -> !next.contains(card) }
    }
    private fun getRemoved(curr: Set<Card>, next: Set<Card>): List<Card> {
        return curr.filter { card -> !next.contains(card) }
    }

    private fun <T> getRemoved(curr: Kit<T>, next: Kit<T>): List<T> {
        return curr.filter { card -> !next.contains(card.key) || (next.count(card.key) > curr.count(card.key)) }
            .map { it.key }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return false
    }

    override fun toString(): String {
        return "$name ($hp❤ $phase mana=${mana.flatMap { k -> (1..k.value).map { k.key } }}, hand=$hand, lands=$lands, battlefield=$battlefield, numberCourse=$numberCourse, isLandPlayable=$isLandPlayable, graveyard=$graveyard, deck=$deck)"
    }

}


