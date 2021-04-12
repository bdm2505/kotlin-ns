package ru.bdm.mtg.cards.chips

import ru.bdm.mtg.Status
import ru.bdm.mtg.cards.creatures.Creature

class BlackDaemon : Creature(5, 5) {

    init {
        status.add(Status.FLYING)
    }
}