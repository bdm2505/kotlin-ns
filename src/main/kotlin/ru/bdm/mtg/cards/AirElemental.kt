package ru.bdm.mtg.cards

import ru.bdm.mtg.Actions
import ru.bdm.mtg.RotateCard
import ru.bdm.mtg.conditions.enoughMana
import ru.bdm.mtg.conditions.inHand
import ru.bdm.mtg.move
import ru.bdm.mtg.spendMana

class AirElemental : RotateCard(true) {

    init {
        act({
            inHand() && enoughMana()
        },{
            spendMana()
            move(me.hand, me.battlefield)
        })
    }
}