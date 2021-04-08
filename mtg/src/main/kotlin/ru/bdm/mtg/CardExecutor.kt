package ru.bdm.mtg

import ru.bdm.mtg.cards.Creature
import ru.bdm.mtg.cards.CreatureExecutor

object CardExecutor {

    private val cardExecutor = Executor()
    private val landExecutor = LandExecutor()
    private val creatureExecutor = CreatureExecutor()

    fun resultStates(state: State, cards: List<AbstractCard>): List<State> {
        return cards.flatMap {
            executor(it).resultStates(state, it) }
    }

    fun executeAll(state: State, cards: List<AbstractCard>) {
        cards.forEach { executor(it).executeAll(state, it) }
    }

    private fun executor(card: AbstractCard) = when (card) {
        is Creature -> creatureExecutor
        is Land -> landExecutor
        else -> cardExecutor
    }
}


open class Executor : CardInterface {
    override lateinit var abstractCard: AbstractCard
    override lateinit var state: State
    private val cond = "condition"
    private val reac = "reaction"

    private fun activeReactions(stateNew: State, cardNew: AbstractCard): List<() -> Unit> {
        state = stateNew
        abstractCard = cardNew
        val list = mutableListOf<() -> Unit>()
        for (method in javaClass.methods)
            if (method.name.startsWith(cond)) {
                if (method.invoke(this) as Boolean) {
                    val name = method.name.substring(cond.length)
                    val funs = javaClass.getMethod(reac + name).invoke(this) as List<() -> Unit>
                    list.addAll(funs)
                }
            }
        return list
    }

    fun resultStates(stateNew: State, cardNew: AbstractCard): List<State> {
        val st = stateNew.clone()
        val card = cardNew.copy()
        st.updateCard(card)
        return activeReactions(st, card).map {
            it()
            val res = state
            state = stateNew.clone()
            state.updateCard(card.copy())
            res
        }
    }

    fun executeAll(stateNew: State, card: AbstractCard) {
        for (funs in activeReactions(stateNew, card))
            funs()
    }
}


