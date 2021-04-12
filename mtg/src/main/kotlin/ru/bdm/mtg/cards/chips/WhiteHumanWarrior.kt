package ru.bdm.mtg.cards.chips

import ru.bdm.mtg.Tag
import ru.bdm.mtg.cards.creatures.Creature

class WhiteHumanWarrior : Creature(1, 1) {

    init {
        tag(Tag.HUMAN, Tag.WARRIOR)
    }
}