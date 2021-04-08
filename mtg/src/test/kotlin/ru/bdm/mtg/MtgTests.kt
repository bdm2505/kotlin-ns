import org.junit.jupiter.api.Test
import ru.bdm.mtg.*

class MtgTests {

    @Test
    fun stateCopy() {
        val state = StatePlayer(
            mana = emptyKit<Mana>() + Mana.RED,
            lands = mutableSetOf(Land(), Land()),
            deck = dequeOf(Land()),
            hand = mutableSetOf(Land())
        )

        val stateCopy = state.copy()
        assert(state !== stateCopy)
        assert(state.mana !== stateCopy.mana)
        assert(state.hand !== stateCopy.hand)
        assert(state.hand.iterator().next() == stateCopy.hand.iterator().next())
        assert(state.hand.iterator().next() !== stateCopy.hand.iterator().next())
        (state.hand.iterator().next() as Land).color = Mana.BLACK
        assert(state.hand.iterator().next() != stateCopy.hand.iterator().next())

        assert(state.battlefield !== stateCopy.battlefield)
        assert(state.deck !== stateCopy.deck)
        assert(state.lands !== stateCopy.lands)
        assert(state.mana['R'] == stateCopy.mana['R'])
        assert(state.deck.first() !== stateCopy.deck.first())
        assert(state.lands.first() !== stateCopy.lands.first())
        assert(state.lands.first() == stateCopy.lands.first())
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
    fun whenExtends(){
        val a: A = C()
        when(a){
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