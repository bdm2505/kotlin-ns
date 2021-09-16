package ru.bdm.mtg.cards.lands

import kotlinx.serialization.Serializable
import ru.bdm.mtg.Mana

@Serializable
class Plains : Land(Mana.WHITE)

class PlainsExecutor : LandExecutor()