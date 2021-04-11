package ru.bdm.mtg

import kotlin.reflect.KClass

object CardExecutor {

    val executors: MutableMap<KClass<*>, Executor> = mutableMapOf()

    init {
        executors[Card::class] = Executor()

        val card = Card()

        executors[card::class]
    }

    fun isRegistered(kclass: KClass<*>) = executors.containsKey(kclass)

    fun register(kclass: KClass<*>, executor: Executor) {
        executors[kclass] = executor
    }

    fun resultStates(battleState: BattleState, cards: List<AbstractCard>): List<BattleState> {
        return cards.flatMap {
            executor(it).resultStates(battleState, it)
        }
    }

    fun executeAll(battleState: BattleState, cards: List<AbstractCard>) {
        cards.forEach { executor(it).executeAll(battleState, it) }
    }

    private fun executor(card: AbstractCard): Executor = executors[card::class]!!
}


open class Executor : CardInterface {
    override lateinit var abstractCard: AbstractCard
    override lateinit var state: BattleState

    private val actions: MutableMap<() -> Boolean, () -> List<() -> Unit>> = mutableMapOf()

    fun one(cond: () -> Boolean, react: () -> Unit) {
        actions[cond] = { listOf(react) }
    }

    fun any(cond: () -> Boolean, reacts: () -> List<() -> Unit>) {
        actions[cond] = reacts
    }

    private fun activeReactions(): List<() -> Unit> {
        val a = actions.flatMap { if (it.key()) it.value() else listOf() }
        println("count reactions = ${a.size} $abstractCard")
        return a
    }

    fun resultStates(battleStateNew: BattleState, cardNew: AbstractCard): List<BattleState> {
        state = battleStateNew.clone()
        abstractCard = cardNew.copy()
        state.updateCard(card)
        return activeReactions().map {
            it()
            val res = state
            state = battleStateNew.clone()
            abstractCard = cardNew.copy()
            state.updateCard(abstractCard)
            res
        }
    }


    fun executeAll(battleStateNew: BattleState, card: AbstractCard) {
        state = battleStateNew
        abstractCard = card
        state.updateCard(card)
        for (funs in activeReactions())
            funs()
    }
}

