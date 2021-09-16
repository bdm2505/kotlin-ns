package ru.bdm.mtg.cards.lands

import kotlinx.serialization.Serializable
import ru.bdm.mtg.Mana

@Serializable
class DismalBackwater : TwoColorAndAddLifeLand(Mana.BLUE, Mana.BLACK)

class DismalBackwaterExecutor : TwoColorAndAddLifeLandExecutor()