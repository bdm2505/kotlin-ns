package ru.bdm.mtg.cards.creatures

import org.junit.jupiter.api.Test
import ru.bdm.mtg.*
import ru.bdm.mtg.cards.chips.WhiteHumanWarrior

internal class UsherOfTheFallenTest {
    @Test
    fun test() {
        Battle().apply {
            val ush = UsherOfTheFallen()
            val cre = Creature(0, 4)
            me.mana add Mana.WHITE add Mana.NEUTRAL
            me.add(Place.BATTLEFIELD, ush)
            enemy.add(Place.BATTLEFIELD, cre)
            turnToEnd()
            nextTurn()
            assert(me.battlefield.size == 2)
            assert(me.battlefield.map { me(it) is WhiteHumanWarrior }.contains(true))
            assert(me.mana.isEmpty())

        }
    }
}