package ru.bdm.mtg.cards.lands

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import ru.bdm.mtg.Battle
import ru.bdm.mtg.CardExecutor
import ru.bdm.mtg.Place

internal class TwoColorAndAddLifeLandTest {


    @TestFactory
    fun `test two color and add life`(): List<DynamicTest> {
        val lands = listOf(
            BloodfellCaves(),
            BlossomingSands(),
            DismalBackwater(),
            JungleHollow(),
            ScouredBarrens(),
            SwiftwaterCliffs()
        )
        return lands.map {
            DynamicTest.dynamicTest(" land ${it}") {
                Battle().apply {
                    val card = it
                    val hpStart = me.hp
                    me.add(Place.HAND, card)
                    nextTurn()

                    assert(me.lands.contains(card.id))
                    assert(me.hp == hpStart + 1)
                    val states = CardExecutor.resultStates(state, listOf(card))
                    assert(states.size == 2)
                }
            }
        }
    }
}