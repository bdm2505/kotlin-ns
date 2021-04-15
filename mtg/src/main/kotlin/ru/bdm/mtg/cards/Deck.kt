package ru.bdm.mtg.cards

import ru.bdm.mtg.AbstractCard
import ru.bdm.mtg.cards.creatures.*
import ru.bdm.mtg.cards.lands.Plains

object Deck {
    val simple = listOf(
        List(20) { Plains() },
        List(2) { UsherOfTheFallen() },
        List(3) { SkyclaveApparition() },
        List(4) { SeasonedHallowblade() },
        List(4) { LuminarchAspirant() },
        List(4) { GiantKiller() },
    ).flatten()
}

private fun swap(deck: MutableList<AbstractCard>, x1: Int, x2: Int) {
    val card = deck[x1]
    deck[x1] = deck[x2]
    deck[x2] = card
}