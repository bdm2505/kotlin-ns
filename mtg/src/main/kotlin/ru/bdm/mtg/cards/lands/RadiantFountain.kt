package ru.bdm.mtg.cards.lands

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.Mana


class RadiantFountainExecutor : LandExecutor() {
    override fun playLand() {
        super.playLand()
        me().hp += 2
    }
}

@Serializable
@SerialName("RadiantFountain")
class RadiantFountain : Land(Mana.NEUTRAL) {
}