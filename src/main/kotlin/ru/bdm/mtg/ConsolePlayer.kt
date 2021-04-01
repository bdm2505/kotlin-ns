package ru.bdm.mtg

class ConsolePlayer(val name: String) : Player(){

    override fun chooseAction(current: State, states: List<State>): State {
        val set = HashSet(states).toList()
        println("course : ${current.first.numberCourse} player $name ")
        println("enemy: ${current.second.battlefield}")
        println(current.first)
        for(i in set.indices){
            println("[$i] : ${current.getDifference(set[i])}")
        }
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