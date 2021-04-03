package ru.bdm.mtg.cards


import ru.bdm.mtg.*
import ru.bdm.mtg.conditions.canPlay

class AirElemental : Creature(4, 4) {

    init {
        cost("CCCUU")
        tag(Tag.CREATURE, Tag.ELEMENTAL)
        status(Status.FLYING)
    }


}