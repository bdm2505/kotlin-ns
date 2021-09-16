package ru.bdm.mtg.cards.lands

import kotlinx.serialization.Serializable
import ru.bdm.mtg.Mana

@Serializable
class BlossomingSands : TwoColorAndAddLifeLand(Mana.GREEN, Mana.WHITE)

class BlossomingSandsExecutor : TwoColorAndAddLifeLandExecutor()