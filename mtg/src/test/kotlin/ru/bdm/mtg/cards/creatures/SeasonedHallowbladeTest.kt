package ru.bdm.mtg.cards.creatures

import org.junit.jupiter.api.Test
import ru.bdm.mtg.*

internal class SeasonedHallowbladeTest {

    @Test
    fun test() {
        Battle().apply {
            val sea = SeasonedHallowblade()
            val cre1 = Creature(4, 4).apply { cost = "RRRRR".toMana() }
            val cre2 = Creature(4, 4).apply { cost = "RRRRR".toMana() }
            val cre3 = Creature(5, 5).apply { isWentOnBattlefield = true }
            me.add(Place.HAND, sea, cre1, cre2)
            enemy.add(Place.BATTLEFIELD, cre3)
            me.mana.addAll("CW".toMana())
            turnToEnd()
            assert(me.battlefield.contains(sea.id))
            nextTurn()
            turnToEnd()
            nextTurn()
            assert(me.battlefield.contains(sea.id))
            assert(me.hand.contains(cre2.id))
            me.get(sea).attack = true
            enemy.get(cre3).rotated = false
            nextTurn()
            assert(me.hand.isEmpty())
            assert(me.get(sea).indestructible)
            turnToEnd()

            assert(enemy.get(cre3).hp == 2)
            assert(me.battlefield.contains(sea.id))


        }
    }
}