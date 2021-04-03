package ru.bdm.mtg

import ru.bdm.*

class StatePlayer(
    val mana: Kit<Mana> = emptyKit(),
    val hand: Kit<Card> = emptyKit(),
    val lands: Kit<Card> = emptyKit(),
    val battlefield: Kit<Card> = emptyKit(),
    var numberCourse: Int = 0,
    var isLandPlayable: Boolean = false,
    val graveyard: ArrayDeque<Card> = ArrayDeque(),
    val deck: ArrayDeque<Card> = ArrayDeque(),
) : Copied {
    fun getDifference(next: StatePlayer): Difference {
        return Difference(
            getAdd(hand, next.hand),
            getAdd(lands, next.lands),
            getAdd(deck, next.deck),
            getAdd(graveyard, next.graveyard),
            getRemoved(hand, next.hand),
            getRemoved(lands, next.lands),
            getRemoved(deck, next.deck),
            getRemoved(graveyard, next.graveyard),
            next.numberCourse
        )
    }

    private fun getAdd(curr: List<Card>, next: List<Card>): List<Card> {
        return next.filter { card -> !curr.contains(card) }
    }

    private fun getAdd(curr: Kit<Card>, next: Kit<Card>): List<Card> {
        return next.filter { card -> !curr.contains(card.key) || (curr.count(card.key) < next.count(card.key)) }.map { it.key }
    }

    private fun getRemoved(curr: List<Card>, next: List<Card>): List<Card> {
        return curr.filter { card -> !next.contains(card) }
    }

    private fun getRemoved(curr: Kit<Card>, next: Kit<Card>): List<Card> {
        return curr.filter { card -> !next.contains(card.key) || (next.count(card.key) > curr.count(card.key)) }.map { it.key }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StatePlayer

        if (hand != other.hand) return false
        if (lands != other.lands) return false
        if (deck != other.deck) return false
        if (battlefield != other.battlefield) return false
        if (graveyard != other.graveyard) return false
        if (isLandPlayable != other.isLandPlayable) return false
        if (numberCourse != other.numberCourse) return false

        return true
    }

    override fun toString(): String {
        return "(mana=${mana.flatMap { k -> (1..k.value).map { k.key } }}, hand=$hand, lands=$lands, battlefield=$battlefield, numberCourse=$numberCourse, isLandPlayable=$isLandPlayable, graveyard=$graveyard, deck=$deck)"
    }

    override fun copy(): StatePlayer {
        return StatePlayer(
            HashMap(mana),
            hand.copy(),
            lands.copy(),
            battlefield.copy(),
            numberCourse,
            isLandPlayable,
            graveyard.copy(),
            deck.copy()
        )
    }

}



data class Difference(
    val handAdd: List<Card>,
    val landsAdd: List<Card>,
    val deckAdd: List<Card>,
    val graveyardAdd: List<Card>,
    val handRemoved: List<Card>,
    val landsRemoved: List<Card>,
    val deckRemoved: List<Card>,
    val graveyardRemoved: List<Card>,
    val numberCourse: Int
) {
    override fun toString(): String {
        return "Difference(" +
                (if (handAdd.isEmpty()) "" else "handAdd=$handAdd,") +
                (if (landsAdd.isEmpty()) "" else " landsAdd=$landsAdd,") +
                (if (deckAdd.isEmpty()) "" else " deckAdd=$deckAdd,") +
                (if (graveyardAdd.isEmpty()) "" else " graveyardAdd=$graveyardAdd,") +
                (if (handRemoved.isEmpty()) "" else " handRemoved=$handRemoved,") +
                (if (landsRemoved.isEmpty()) "" else " landsRemoved=$landsRemoved,") +
                (if (deckRemoved.isEmpty()) "" else " deckRemoved=$deckRemoved,") +
                (if (graveyardRemoved.isEmpty()) "" else " graveyardRemoved=$graveyardRemoved,") +
                " numberCourse=$numberCourse)"
    }

}