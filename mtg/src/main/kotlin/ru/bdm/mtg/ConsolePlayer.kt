package ru.bdm.mtg

class ConsolePlayer(name: String) : Player(name){

    override fun chooseAction(current: State, states: List<State>): State {
        val set = HashSet(states).toList()
        println("course : ${current.me.numberCourse} player $name ")
        println("enemy ${current.enemy.hp}❤: ${current.enemy.battlefield}")
        println(current.me)
        for(i in set.indices){
            println("[$i] : ${current.getDifference(set[i])}")
        }
//        if (states.size == 1)
//            return set[0]
        print("choose number:")
        return set[0]
        val res = readLine()!!.toInt()
        return set[res]
    }

}

fun main(){
    val battle = Battle(ConsolePlayer("me"), ConsolePlayer("enemy"))
    while (!battle.isEnd()){
        battle.nextTurn()
    }
    println()
    println(battle.state)
}