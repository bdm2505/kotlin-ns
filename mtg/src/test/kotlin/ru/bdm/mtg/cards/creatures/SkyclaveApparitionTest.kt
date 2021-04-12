package ru.bdm.mtg.cards.creatures

import org.junit.jupiter.api.Test
import ru.bdm.mtg.*

internal class SkyclaveApparitionTest {
    @Test
    fun test() {
        Battle().apply {
            val sky = SkyclaveApparition()
            val cr1 = Creature(2, 2).apply { cost = "CCC".toMana() }
            val cr2 = Creature(5, 5).apply { cost = "RRRRRR".toMana() }
            me.add(Place.HAND, sky)
            enemy.add(Place.BATTLEFIELD, cr1, cr2)
            me.mana.addAll("CWW".toMana())
            nextTurn()
            println(state)
            assert(me.mana.isEmpty())
            assert(enemy.battlefield.size == 1)
            assert(me.battlefield.size == 1)
            me.get(sky).attack = true
            turnToEnd()
            assert(enemy.battlefield.size == 2)
            assert(me.battlefield.size == 0)
            assert(enemy.battlefield.map { enemy<Creature>(it).force == 3 }.contains(true))

        }
    }
}