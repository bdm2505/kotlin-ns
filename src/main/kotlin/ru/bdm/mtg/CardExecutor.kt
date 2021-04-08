package ru.bdm.mtg

object CardExecutor {

    private val landExecutor = LandExecutor()
    private val cardExecutor = Executor()

    fun resultStates(state: State, cards: List<Card>): List<State> {
        return cards.flatMap {
            executor(it).resultStates(state, it) }
    }

    fun executeAll(state: State, cards: List<Card>) {
        cards.forEach { executor(it).executeAll(state, it) }
    }

    private fun executor(card: Card) = when (card) {
        is Land -> landExecutor
        else -> cardExecutor
    }
}


open class Executor : CardInterface {
    override lateinit var state: State
    override lateinit var card: Card
    val cond = "condition"
    val reac = "reaction"

    private fun activeReactions(stateNew: State, cardNew: Card): List<() -> Unit> {
        state = stateNew
        card = cardNew
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

    fun resultStates(stateNew: State, cardNew: Card): List<State> {
        return activeReactions(stateNew.clone(), cardNew.copy()).map {
            it()
            val res = state
            state = stateNew.clone()
            res
        }
    }

    fun executeAll(stateNew: State, card: Card) {
        for (funs in activeReactions(stateNew, card))
            funs()
    }
}
