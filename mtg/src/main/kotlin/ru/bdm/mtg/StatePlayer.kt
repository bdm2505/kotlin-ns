package ru.bdm.mtg

import kotlinx.serialization.Serializable

@Serializable
class StatePlayer(
    val name: String = "no_name",
    val mana: Kit<Mana> = emptyKit(),
    val cards: MutableMap<Int, AbstractCard> = mutableMapOf(),
    val hand: MutableSet<Int> = mutableSetOf(),
    val lands: MutableSet<Int> = mutableSetOf(),
    val battlefield: MutableSet<Int> = mutableSetOf(),
    val graveyard: MutableList<Int> = mutableListOf(),
    val deck: MutableList<Int> = mutableListOf(),
    val exile: MutableSet<Int> = mutableSetOf(),
    var numberCourse: Int = 0,
    var isLandPlayable: Boolean = false,
    var hp: Int = 20,
) : Copied {
    override fun copy(): StatePlayer {
        return StatePlayer(
            name,
            HashMap(mana),
            cards.map { Pair(it.key, it.value.copy()) }.toMap().toMutableMap(),
            hand.toMutableSet(),
            lands.toMutableSet(),
            battlefield.toMutableSet(),
            graveyard.toMutableList(),
            deck.toMutableList(),
            exile.toMutableSet(),
            numberCourse,
            isLandPlayable,
            hp
        )
    }

    fun getDifference(next: StatePlayer): Difference {
        if (name != next.name)
            return Difference(
                null,
                listOf(),
                null,
                "$name->${next.name}",
                if (numberCourse != next.numberCourse) next.numberCourse else null,
                null,
            )
        else {
            val list = mutableListOf<Pair<AbstractCard, String>>()
            for ((index, card) in next.cards) {
                if (cards.contains(index)) when {
                    cards[index]!! notEq card ->
                        list.add(Pair(card, ""))
                    next.hand.contains(index) && !hand.contains(index) ->
                        list.add(Pair(card, "->hand"))
                    next.lands.contains(index) && !lands.contains(index) ->
                        list.add(Pair(card, "->lands"))
                    next.battlefield.contains(index) && !battlefield.contains(index) ->
                        list.add(Pair(card, "->battlefield"))
                    next.graveyard.contains(index) && !graveyard.contains(index) ->
                        list.add(Pair(card, "->graveyard"))
                    next.deck.contains(index) && !deck.contains(index) ->
                        list.add(Pair(card, "->deck"))


                }
            }
            return Difference(
                if (mana.string() != next.mana.string()) next.mana.string() else null,
                list,
                if (hp != next.hp) next.hp else null,
                name,
                if (numberCourse != next.numberCourse) next.numberCourse else null,
                if (isLandPlayable != next.isLandPlayable) next.isLandPlayable else null,
            )
        }
    }


    override fun toString(): String {
        return "$name (${hp}hp mana=${mana.string()}, cards=$cards, hand=$hand, lands=$lands, battlefield=$battlefield, numberCourse=$numberCourse, isLandPlayable=$isLandPlayable, graveyard=$graveyard, deck=$deck)"
    }

    fun updateCard(card: AbstractCard) {
        if (cards.contains(card.id))
            cards[card.id] = card
    }

    fun activeCards(): List<AbstractCard> = (hand + battlefield + lands).map { cards[it]!! }

    fun add(place: Place, vararg cardsNew: AbstractCard) {
        val coll = set(place)
        for (card in cardsNew) {
            coll += card.id
            cards[card.id] = card
        }
    }

    fun set(place: Place): MutableCollection<Int> = when (place) {
        Place.BATTLEFIELD -> battlefield
        Place.HAND -> hand
        Place.DECK -> deck
        Place.LANDS -> lands
        Place.GRAVEYARD -> graveyard
        Place.EXILE -> exile
    }

    fun place(set: MutableCollection<Int>) = when (set) {
        battlefield -> Place.BATTLEFIELD
        hand -> Place.HAND
        lands -> Place.LANDS
        deck -> Place.DECK
        graveyard -> Place.GRAVEYARD
        exile -> Place.EXILE
        else -> throw Exception("no correct get Place in $set ")
    }


    fun addAll(cardsNew: List<AbstractCard>, place: Place = Place.HAND) {
        add(place, *cardsNew.toTypedArray())
    }

    fun addInDeck(vararg cardsNew: AbstractCard) {
        for (card in cardsNew) {
            deck += card.id
            cards[card.id] = card
        }
    }

    operator fun invoke(index: Int): AbstractCard = cards[index]!!

    operator fun <T> invoke(index: Int): T = cards[index]!! as T

    fun <T> get(card: T): T where T : AbstractCard = cards[card.id]!! as T
}


