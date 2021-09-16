package ru.bdm.mtg.cards.lands

import kotlinx.serialization.Serializable
import ru.bdm.mtg.Executor
import ru.bdm.mtg.Mana


open class TwoColorAndAddLifeLandExecutor : TwoColorLandExecutor() {
    override fun playLandAndRotate() {
        super.playLandAndRotate()
        me().hp += 1
    }
}

@Serializable
open class TwoColorAndAddLifeLand : TwoColorLand {
    constructor() : super()
    constructor(colorOne: Mana, colortwo: Mana) : super(colorOne, colortwo)

}