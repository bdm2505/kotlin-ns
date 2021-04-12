package ru.bdm.mtg.cards.creatures


import kotlinx.serialization.Serializable
import ru.bdm.mtg.Status
import ru.bdm.mtg.Tag

@Serializable
class AirElemental : Creature(4, 4) {

    init {
        cost("CCCUU")
        tag(Tag.CREATURE, Tag.ELEMENTAL)
        status(Status.FLYING)
    }
}