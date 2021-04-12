import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.bdm.mtg.Mana
import ru.bdm.mtg.Place
import ru.bdm.mtg.StatePlayer
import ru.bdm.mtg.cards.creatures.Creature
import ru.bdm.mtg.cards.lands.Land
import ru.bdm.mtg.toMana

class MtgTests {

    @Test
    fun stateCopy() {
        val land = Land()
        val state = StatePlayer(mana = "RRC".toMana()).apply {
            add(Place.HAND, land, Land())
            addInDeck(Land(), Land())
        }


        val stateCopy = state.copy()
        assert(state !== stateCopy)
        assert(state.mana !== stateCopy.mana)
        assert(state.hand !== stateCopy.hand)
        assert(state.hand.iterator().next() == stateCopy.hand.iterator().next())

        (state(land.id) as Land).color = Mana.BLACK
        println("${state(land.id)} ${stateCopy(land.id)}")
        assert(state(land.id) notEq stateCopy(land.id))

        assert(state.battlefield !== stateCopy.battlefield)
        assert(state.deck !== stateCopy.deck)
        assert(state.lands !== stateCopy.lands)
        assertEquals(state.mana[Mana.RED], stateCopy.mana[Mana.RED])
    }

    @Test
    fun testClone() {
        val a = A()
        val ca = a.clone()
        assert(a !== ca)
        assert(a.a == ca.a)
        a.a = 1
        assert(a.a != ca.a)
        val b = B(a)
        val cb = b.clone()
        assert(b !== cb)
        assert(b.aa === cb.aa)
        assert(b.a == cb.a)

    }

    @Test
    fun testCloneCreature() {
        val cr = Creature(4, 5).apply { attack = true }
        val cloCr = cr.copy() as Creature
        assert(cr eq cloCr)
    }

    @Test
    fun whenExtends() {
        val a: A = C()
        when (a) {
            is B ->
                println("B")
            is A ->
                println("A")
        }
    }


}

open class A : Cloneable {
    var a: Int = 10
    public override fun clone(): A {
        return super.clone() as A
    }
}

class B(var aa: A = A()) : A() {
    override fun clone(): B {
        return super.clone() as B
    }
}
class C : A()