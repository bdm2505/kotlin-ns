package ru.bdm.mtg.cards.lands

import kotlinx.serialization.Serializable
import ru.bdm.mtg.Mana

@Serializable
class JungleHollow : TwoColorAndAddLifeLand(Mana.BLACK, Mana.GREEN)