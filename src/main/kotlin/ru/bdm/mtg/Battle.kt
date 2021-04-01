package ru.bdm.mtg

typealias State = Pair<StatePlayer, StatePlayer>
class Battle (val player : Player, val enemyPlayer : Player){
    var me : StatePlayer = StatePlayer()
    var enemy : StatePlayer = StatePlayer()
    var myMove: Boolean = true

    init {
       me.hand +=  mutableMapOf(Pair(Land(), 5))
       enemy.hand +=  mutableMapOf(Pair(Land(), 5))
    }

    fun nextTurn(){
        if (myMove){
            val next = turn(me, enemy, player)
            if(me.numberCourse != next.first.numberCourse)
                myMove = false
            me = next.first
            enemy = next.second
        } else {
            val next = turn(enemy, me, enemyPlayer)
            if(enemy.numberCourse != next.first.numberCourse)
                myMove = true
            enemy = next.first
            me = next.second
        }
    }

    private fun turn(me: StatePlayer, enemy: StatePlayer, player: Player): State {
        val actions = sequence {
            for(card in me.hand.keys){
                card.setStates(me, enemy)
                for(act in card.actions){
                    if(act.first.invoke()) {
                        val meCopy = me.copy()
                        val enemyCopy = enemy.copy()
                        act.second.invoke()
                        yield(Pair(meCopy, enemyCopy))
                    }
                }
            }
            val meCopy = me.copy()
            val enemyCopy = enemy.copy()
            meCopy.mana.clear()
            meCopy.numberCourse += 1
            meCopy.isLandPlayable = false
            yield(Pair(meCopy, enemyCopy))
        }
        return player.chooseAction(Pair(me, enemy), actions.toList())
    }

}
fun State.getDifference(next : State): Pair<Difference, Difference> {
    return Pair(first.getDifference(next.first), second.getDifference(next.second))
}