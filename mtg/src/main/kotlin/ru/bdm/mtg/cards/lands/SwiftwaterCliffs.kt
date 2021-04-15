package ru.bdm.mtg.cards.lands

import kotlinx.serialization.Serializable
import ru.bdm.mtg.Mana

@Serializable
class SwiftwaterCliffs : TwoColorAndAddLifeLand(Mana.BLUE, Mana.RED) {
}