package ru.bdm.mtg

import ru.bdm.mtg.cards.Creature
import ru.bdm.mtg.cards.CreatureExecutor

object CardExecutor {

    private val cardExecutor = Executor()
    private val landExecutor = LandExecutor()
    private val creatureExecutor = CreatureExecutor()

    fun resultStates(battleState: BattleState, cards: List<AbstractCard>): List<BattleState> {
        return cards.flatMap {
            executor(it).resultStates(battleState, it) }
    }

    fun executeAll(battleState: BattleState, cards: List<AbstractCard>) {
        cards.forEach { executor(it).executeAll(battleState, it) }
    }

    private fun executor(card: AbstractCard) = when (card) {
        is Creature -> creatureExecutor
        is Land -> landExecutor
        else -> cardExecutor
    }
}


open class Executor : CardInterface {
    override lateinit var abstractCard: AbstractCard
    override lateinit var battleState: BattleState
    private val cond = "condition"
    private val reac = "reaction"

    private fun activeReactions(battleStateNew: BattleState, cardNew: AbstractCard): List<() -> Unit> {
        battleState = battleStateNew
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

    fun resultStates(battleStateNew: BattleState, cardNew: AbstractCard): List<BattleState> {
        val st = battleStateNew.clone()
        val card = cardNew.copy()
        st.updateCard(card)
        return activeReactions(st, card).map {
            it()
            val res = battleState
            battleState = battleStateNew.clone()
            battleState.updateCard(card.copy())
            res
        }
    }

    fun executeAll(battleStateNew: BattleState, card: AbstractCard) {
        for (funs in activeReactions(battleStateNew, card))
            funs()
    }
}


