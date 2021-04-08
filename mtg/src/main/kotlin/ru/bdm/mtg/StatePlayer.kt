package ru.bdm.mtg

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
class StatePlayer(
    val name: String = "no_name",
    val mana: Kit<Mana> = emptyKit(),
    val hand: MutableSet<AbstractCard> = mutableSetOf(),
    val lands: MutableSet<AbstractCard> = mutableSetOf(),
    val battlefield: MutableSet<AbstractCard> = mutableSetOf(),
    var numberCourse: Int = 0,
    var isLandPlayable: Boolean = false,
    val graveyard: MutableList<AbstractCard> = mutableListOf(),
    val deck: MutableList<AbstractCard> = mutableListOf(),
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

    private fun getAdd(curr: List<AbstractCard>, next: List<AbstractCard>): List<AbstractCard> {
        return next.filter { card -> !curr.contains(card) }
    }

    private fun getAdd(curr: Set<AbstractCard>, next: Set<AbstractCard>): List<AbstractCard> {
        return next.filter { card -> !curr.contains(card) }
    }

    private fun <T> getAdd(curr: Kit<T>, next: Kit<T>): List<T> {
        return next.filter { card -> !curr.contains(card.key) || (curr.count(card.key) < next.count(card.key)) }
            .map { it.key }
    }

    private fun getRemoved(curr: List<AbstractCard>, next: List<AbstractCard>): List<AbstractCard> {
        return curr.filter { card -> !next.contains(card) }
    }
    private fun getRemoved(curr: Set<AbstractCard>, next: Set<AbstractCard>): List<AbstractCard> {
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

    fun updateCard(card: AbstractCard) {
        if (hand.contains(card)) {
            hand -= card
            hand += card
        } else if (battlefield.contains(card)) {
            battlefield -= card
            battlefield += card
        } else if (lands.contains(card)) {
            lands -= card
            lands += card
        } else if (graveyard.contains(card)){
            val index = graveyard.indexOf(card)
            graveyard[index] = card
        } else if (deck.contains(card)){
            val index = deck.indexOf(card)
            deck[index] = card
        }

    }

    fun activeCards(): Set<AbstractCard> = (hand + battlefield + lands)
}


