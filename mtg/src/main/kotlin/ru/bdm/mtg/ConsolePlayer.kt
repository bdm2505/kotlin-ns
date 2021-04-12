package ru.bdm.mtg

import ru.bdm.mtg.cards.creatures.Creature

open class ConsolePlayer(name: String) : Player(name){

    override fun chooseAction(current: BattleState, battleStates: List<BattleState>): BattleState {
        printLog(battleStates, current)
        val res = readLine()!!.toInt()
        return battleStates[res]
    }
    fun printLog(battleStates: List<BattleState>, current: BattleState) {
        println("course : ${current.me.numberCourse} player $name ")
        println("enemy ${current.enemy.hp}❤: ${current.enemy.battlefield}")
        println(current.me)
        for (i in battleStates.indices) {
            println("[$i] : ${current.getDifference(battleStates[i])}")
        }
        print("choose number:")
    }
}

class ZeroPlayer(name: String, val log: Boolean = false): ConsolePlayer(name){
    override fun chooseAction(current: BattleState, battleStates: List<BattleState>): BattleState {
        if(log) {
            printLog(battleStates, current)
            println(0)
        }
        return battleStates[0]
    }
}


fun main(){
    val battle = Battle(ConsolePlayer("me"), ConsolePlayer("enemy")).apply {
        me.addAll(List(3) { Creature(3, 4) })
        enemy.addAll(List(4) { Creature(4, 2) })
    }
    while (!battle.isEnd()){
        battle.nextTurn()
    }
    println()
    println(battle.state)
}