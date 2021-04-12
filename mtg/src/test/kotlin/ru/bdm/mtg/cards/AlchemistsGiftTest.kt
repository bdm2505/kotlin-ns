package ru.bdm.mtg.cards

import org.junit.jupiter.api.Test
import ru.bdm.mtg.*
import ru.bdm.mtg.cards.creatures.Creature
import ru.bdm.mtg.cards.spells.AlchemistsGift

internal class AlchemistsGiftTest {
    @Test
    fun test() {
        Battle().apply {
            val cre = Creature(2, 2)
            val alc = AlchemistsGift()
            me.add(Place.BATTLEFIELD, cre)
            me.add(Place.HAND, alc)
            me.mana add Mana.BLACK
            for (state in nextStates(listOf(alc))) {
                state.apply {
                    println(me.mana)
                    assert(me.mana.isEmpty())
                    assert(me.hand.isEmpty())
                    assert(me.get(cre).hp == 3)
                    assert(me.get(cre).force == 3)
                    assert(me.get(cre).passiveBuffs.contains(Token))
                    assert(me.get(cre).activeBuffs.run {
                        contains(LifeLink) || contains(DeathTouch)
                    })
                }
            }
        }
    }
}