package ru.bdm.mtg.cards.lands

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.Mana

@Serializable
@SerialName("BloodfellCaves")
class BloodfellCaves : TwoColorAndAddLifeLand(Mana.BLACK, Mana.RED)