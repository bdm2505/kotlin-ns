import org.junit.jupiter.api.Test
import ru.bdm.mtg.*

class MtgTests {

    @Test
    fun stateCopy(){
        val state = StatePlayer(mana = kitOf(Pair('R', 3)), lands = kitOf(Pair(Land(), 2)), deck = dequeOf(Land()))
        val stateCopy = state.copy()
        assert(state !== stateCopy)
        assert(state.mana !== stateCopy.mana)
        assert(state.battlefield !== stateCopy.battlefield)
        assert(state.deck !== stateCopy.deck)
        assert(state.lands !== stateCopy.lands)
        assert(state.mana['R'] == stateCopy.mana['R'])
        assert(state.deck.first() !== stateCopy.deck.first())
        assert(state.lands.map { it.key }.first() !== stateCopy.lands.map { it.key }.first())
    }

    @Test
    fun testClone(){
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
    fun testExecuteCard(){
        val state = StatePlayer()
        val card = Land()
        card.setStates(state, state)
        state.hand.add(card)
        val results = mutableListOf<State>()
        executeCard(card, Pair(state, state), results::add)
        results[0].first.apply {
            assert(hand.isEmpty())
            assert(lands.containsKey(card))
        }
    }

    @Test
    fun testManaEquals(){
        assert(Mana.NEUTRAL == Mana.C)
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