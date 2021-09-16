package ru.bdm.mtg

import kotlinx.serialization.Serializable

@Serializable
class StatePlayer(
    val name: String = "no_name",
    val mana: Kit<Mana> = emptyKit(),
    val hand: MutableSet<Int> = mutableSetOf(),
    val lands: MutableSet<Int> = mutableSetOf(),
    val battlefield: MutableSet<Int> = mutableSetOf(),
    val graveyard: MutableList<Int> = mutableListOf(),
    val deck: MutableList<Int> = mutableListOf(),
    val exile: MutableSet<Int> = mutableSetOf(),
    var isLandPlayable: Boolean = false,
    var hp: Int = 20,
) : Copied {
    override fun copy(): StatePlayer {
        return StatePlayer(
            name,
            HashMap(mana),
            hand.toMutableSet(),
            lands.toMutableSet(),
            battlefield.toMutableSet(),
            graveyard.toMutableList(),
            deck.toMutableList(),
            exile.toMutableSet(),
            isLandPlayable,
            hp
        )
    }



    override fun toString(): String {
        return "$name (${hp}hp mana=${mana.string()}, hand=$hand, lands=$lands, battlefield=$battlefield, isLandPlayable=$isLandPlayable, graveyard=$graveyard, deck=$deck)"
    }



    fun activeCards(): Set<Int> = hand + battlefield + lands

    fun add(place: Place, vararg cardsNew: Card) {
        val coll = set(place)
        for (card in cardsNew) {
            coll += card.id
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


    fun inHand(id: Int): Boolean = hand.contains(id)
    fun inLands(id: Int): Boolean = lands.contains(id)
    fun inBattlefield(id: Int): Boolean = battlefield.contains(id)
    fun inExile(id: Int): Boolean = exile.contains(id)
    fun inGraveyard(id: Int): Boolean = graveyard.contains(id)



}


