package ru.bdm.mtg.cards.lands

import kotlinx.serialization.Serializable
import ru.bdm.mtg.Mana

@Serializable
class Island : Land(Mana.BLUE)