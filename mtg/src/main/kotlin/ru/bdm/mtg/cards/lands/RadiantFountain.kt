package ru.bdm.mtg.cards.lands

import ru.bdm.mtg.Mana

interface RadiantFountainInterface : LandInterface {
    override fun playLand() {
        super.playLand()
        me.hp += 2
    }
}

class RadiantFountainExecutor : LandExecutor(), RadiantFountainInterface {

}

class RadiantFountain() : Land(Mana.NEUTRAL) {
    override fun executor() = RadiantFountainExecutor()
}