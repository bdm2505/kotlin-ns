package ru.bdm.mtg

import org.junit.jupiter.api.Test
import ru.bdm.mtg.cards.CardSerializer
import ru.bdm.mtg.cards.Creature
import ru.bdm.mtg.cards.lands.Land

class CardTest {

    @Test
    fun testPlayLandSimple() {
        val land = Land()
        val battle = Battle(ZeroPlayer("one"), ZeroPlayer("two")).apply {
            me.addIn(me.hand, land.copy())
        }

        battle.nextTurn()
        assert(battle.me.lands.contains(land.id))
    }

    @Test
    fun testPlayLandIsLandPlayable() {
        val land = Land()
        val battle = Battle(ZeroPlayer("one"), ZeroPlayer("two")).apply {
            me.addIn(me.hand, land.copy())
        }
        battle.me.isLandPlayable = true

        battle.nextTurn()
        assert(!battle.me.lands.contains(land.id))
    }

    @Test
    fun testRotateLand() {
        val land = Land()
        val battle = Battle(ZeroPlayer("one"), ZeroPlayer("two")).apply {
            me.addIn(me.lands, land.copy())
        }
        battle.apply {
            nextTurn()
            assert((me(land.id) as Land).rotated)
            assert(me.mana.string() == "R")
        }
    }

    @Test
    fun testCanAttack() {
        val creature = Creature(2,3)
        val battle = Battle(ZeroPlayer("one"), ZeroPlayer("two")).apply {
            me.addIn(me.battlefield, creature.copy())
            me.phase = Phase.ATTACK
        }
        battle.apply {
            nextTurn()
            val cr = me(creature.id) as Creature
            assert(cr.attack)
            assert(cr.rotated)
        }
        val creature2 = Creature(1,2).apply {
            rotated = true
        }
        println(battle.me)
        battle.me.apply {

            addIn(battlefield, creature2)
            battlefield -= creature.id
            phase = Phase.ATTACK
        }

        battle.apply {
            nextTurn()
            state = state.swap()
            println(me)
            val cr = me(creature2.id) as Creature
            println(cr)
            assert(!cr.attack)
            assert(cr.rotated)
            assert(me.battlefield == mutableSetOf(creature2.id))
        }
    }

    @Test
    fun testAttackEmptyEnemyBattlefield() {
        Battle(ZeroPlayer("one"), ZeroPlayer("two")).apply {
            val creature = Creature(3, 4)
            me.addIn(me.battlefield, creature)
            turnToEnd()

            assert(me.phase == Phase.END)
            assert(me.get(creature).attack)
            assert(me.get(creature).rotated)
            assert(enemy.hp == me.hp - 3)
        }
    }
    fun Battle.turnToEnd(){
        while (me.phase != Phase.END && enemy.phase != Phase.END){
            println(state)
            nextTurn()
        }
        println(state)
    }

    @Test
    fun testBlockOneAttack(){
        val battle = Battle(ZeroPlayer("one"), ZeroPlayer("two"))
        battle.apply {
            me.apply {
                addIn(battlefield, Creature(2, 4))
            }
            enemy.apply {
                addIn(battlefield, Creature(2,4))
            }
            turnToEnd()
            val cr1 = me(me.battlefield.first()) as Creature
            val cr2 = enemy(enemy.battlefield.first()) as Creature
            assert(cr1.hp == 2)
            assert(cr1.rotated)
            assert(cr1.attack)
            assert(cr2.hp == 2)
            assert(!cr2.rotated)
            assert(!cr2.attack)
        }
    }

    @Test
    fun testBlockSomeoneAttack() {
        Battle(ZeroPlayer("one"), ZeroPlayer("two")).apply {
            me.apply {
                addIn(battlefield, Creature(3, 3).apply { attack = true }, Creature(3, 4).apply { attack = true })
            }
            enemy.addIn(enemy.battlefield, Creature(5, 7))
            turnToEnd()

            assert(me.battlefield.size == 1)
            assert(me.graveyard.size == 1)
            assert((enemy.cards.values.first() as Creature).hp == 4)
            assert(enemy.hp == me.hp - 3)
        }
    }

    @Test
    fun testSomeoneBlockOneAttck() {
        Battle(ZeroPlayer("one"), ZeroPlayer("two")).apply {
            val att = Creature(3, 10)
            val bl1 = Creature(5, 4)
            val bl2 = Creature(5, 4)
            me.addIn(me.battlefield, att)
            enemy.addIn(enemy.battlefield, bl1, bl2)
            turnToEnd()
            assert(me.battlefield.isEmpty())
            assert(me.graveyard.size == 1)

            assert(enemy.get(bl1).hp == 1)
            assert(enemy.get(bl2).hp == 1)
        }
    }

    @Test
    fun testSomeoneBlockSomeoneAttack() {
        Battle(ZeroPlayer("one"), ZeroPlayer("two")).apply {
            val att1 = Creature(3, 10)
            val att2 = Creature(3, 10)
            val att3 = Creature(3, 10)
            val att4 = Creature(3, 10)
            val bl1 = Creature(5, 4)
            val bl2 = Creature(5, 4)
            val bl3 = Creature(5, 4)
            me.addIn(me.battlefield, att1, att2, att3, att4)
            enemy.addIn(enemy.battlefield, bl1, bl2, bl3)
            turnToEnd()
        }
    }

    @Test
    fun testBlockCard() {
        val cr1 = Creature(2, 4)
        val cr2 = Creature(2, 4)
        val state = BattleState().apply {
            me.apply {
                addIn(battlefield, cr1, cr2)
                phase = Phase.BLOCK
            }
            enemy.apply {
                addIn(battlefield, Creature(3, 5).apply { attack = true })
                phase = Phase.BLOCK
            }
        }
        val cards = state.me.cards.values.toList()
        val states = CardExecutor().resultStates(state, cards)
        assert(states[0].me.get(cr1).hp == 1)
        assert(states[0].me.get(cr2).hp == 4)
        assert(states[1].me.get(cr1).hp == 4)
        assert(states[1].me.get(cr2).hp == 1)
    }



    @Test
    fun toJson() {
        val state = BattleState().apply {
            me.apply {
                hp = 90
                addIn(hand, Creature(2, 4))
                addIn(battlefield, Land())
            }
        }

        val str = CardSerializer.encode(state)

        val res = CardSerializer.decode(str)

        assert(res.toString() == state.toString())
    }
    @Test
    fun testLandAddMana() {
        Battle(ZeroPlayer("one"), ZeroPlayer("and")).apply {
            me.apply {
                addIn(lands, Land(Mana.RED))
            }
            nextTurn()
            println(state)
            assert(me.mana == "R".toCost())
            assert(!me.isLandPlayable)
            assert((me.cards.values.first() as Land).rotated)
        }
    }
}
    
    