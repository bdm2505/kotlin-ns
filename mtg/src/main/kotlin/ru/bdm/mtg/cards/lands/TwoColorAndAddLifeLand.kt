package ru.bdm.mtg.cards.lands

import kotlinx.serialization.Serializable
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

@Serializable
open class TwoColorAndAddLifeLand : TwoColorLand {
    constructor() : super()
    constructor(colorOne: Mana, colortwo: Mana) : super(colorOne, colortwo)

    override fun executor(): Executor {
        return TwoColorAndAddLifeLandExecutor()
    }
}