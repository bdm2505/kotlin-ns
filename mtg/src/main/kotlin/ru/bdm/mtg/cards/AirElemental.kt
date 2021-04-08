package ru.bdm.mtg.cards


import kotlinx.serialization.Serializable
import ru.bdm.mtg.*
@Serializable
class AirElemental : Creature(4, 4) {

    init {
        cost("CCCUU")
        tag(Tag.CREATURE, Tag.ELEMENTAL)
        status(Status.FLYING)
    }

}