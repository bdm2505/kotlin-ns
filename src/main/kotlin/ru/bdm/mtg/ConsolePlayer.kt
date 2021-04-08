package ru.bdm.mtg

class ConsolePlayer(name: String) : Player(name){

    override fun chooseAction(current: State, states: List<State>): State {
        val set = HashSet(states).toList()
        println("course : ${current.first.numberCourse} player $name ")
        println("enemy: ${current.second.battlefield}")
        println(current.first)
        for(i in set.indices){
            println("[$i] : ${current.getDifference(set[i])}")
        }
//        if (states.size == 1)
//            return set[0]
        print("choose number:")
        val res = readLine()!!.toInt()
        return set[res]
    }

}

fun main(){
    val battle = Battle(ConsolePlayer("me"), ConsolePlayer("enemy"))
    while (true){
        battle.nextTurn()
    }
}