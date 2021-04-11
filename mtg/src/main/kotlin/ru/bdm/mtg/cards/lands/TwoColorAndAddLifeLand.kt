package ru.bdm.mtg.cards.lands

import ru.bdm.mtg.Executor
import ru.bdm.mtg.Mana

interface TwoColorAndAddLifeLandInterface : TwoColorLandInterface {
    override fun playLandAndRotate() {
        super.playLandAndRotate()
        me.hp += 1
    }
}

class TwoColorAndAddLifeLandExecutor : TwoColorLandExecutor(), TwoColorAndAddLifeLandInterface {

}

open class TwoColorAndAddLifeLand(colorOne: Mana, colortwo: Mana) : TwoColorLand(colorOne, colortwo) {
    override fun executor(): Executor {
        return TwoColorAndAddLifeLandExecutor()
    }
}