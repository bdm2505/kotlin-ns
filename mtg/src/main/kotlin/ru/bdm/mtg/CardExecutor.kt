package ru.bdm.mtg

import kotlin.reflect.KClass

object CardsExecutor {

    val executors: MutableMap<KClass<*>, Executor> = mutableMapOf()

    init {
        executors[Card::class] = Executor()
    }

    fun isRegistered(kclass: KClass<*>) = executors.containsKey(kclass)

    fun register(kclass: KClass<*>, executor: Executor) {
        executors[kclass] = executor
    }

    fun registerAll(cards: List<Card>) {
        for (card in cards) {
            val executorName = card::class.qualifiedName + "Executor"
            print("register $executorName ")
            try {
                val executor = Class.forName(executorName).constructors[0].newInstance() as Executor
                executors[card::class] = executor
                println(" ok!")
            } catch (e : Exception){
                println(" fail!")
                System.err.println("fair register class $executorName")
            }
        }
    }

}


open class Executor  {

    open fun actions(): List<Pair<(Card, BattleState) -> Boolean, Action>> = listOf()

    open fun execute(action: Action, card: Card, state: BattleState) {}

    open fun activeActions(card: Card, state: BattleState): List<Action> = actions().filter { it.first(card, state) }.map { it.second }

}




