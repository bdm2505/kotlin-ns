package ru.bdm.mtg.cards.chips

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.Tag
import ru.bdm.mtg.cards.creatures.Creature

@Serializable
@SerialName("WhiteHumanWarrior")
class WhiteHumanWarrior : Creature(1, 1) {

    init {
        tag(Tag.HUMAN, Tag.WARRIOR)
    }
}