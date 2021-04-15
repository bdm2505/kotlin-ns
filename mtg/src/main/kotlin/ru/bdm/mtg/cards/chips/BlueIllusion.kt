package ru.bdm.mtg.cards.chips

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.bdm.mtg.cards.creatures.Creature

@Serializable
@SerialName("BlueIllusion")
class BlueIllusion : Creature {
    constructor() : super(0, 0)
    constructor(f: Int, h: Int) : super(f = f, hp = h)
}