package ru.bdm.mtg.cards.chips

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.Status
import ru.bdm.mtg.cards.creatures.Creature

@Serializable
@SerialName("BlackDaemon")
class BlackDaemon : Creature(5, 5) {

    init {
        status.add(Status.FLYING)
    }
}