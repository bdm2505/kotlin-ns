package ru.bdm.mtg.cards.lands

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.Mana

interface RadiantFountainInterface : LandInterface {
    override fun playLand() {
        super.playLand()
        me.hp += 2
    }
}

class RadiantFountainExecutor : LandExecutor(), RadiantFountainInterface {

}

@Serializable
@SerialName("RadiantFountain")
class RadiantFountain : Land(Mana.NEUTRAL) {
    override fun executor() = RadiantFountainExecutor()
}