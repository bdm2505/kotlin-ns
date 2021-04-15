package ru.bdm.mtg.cards.lands

import kotlinx.serialization.Serializable
import ru.bdm.mtg.Mana

@Serializable
class ScouredBarrens : TwoColorAndAddLifeLand(Mana.WHITE, Mana.BLACK)