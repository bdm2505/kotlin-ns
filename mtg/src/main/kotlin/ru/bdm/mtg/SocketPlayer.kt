package ru.bdm.mtg

import ru.bdm.mtg.cards.CardSerializer
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.util.*

class SocketPlayer(name: String, port: Int) : Player(name) {

    init {
        println("player $name listen in $port")
    }
    private val server = ServerSocket(port)
    private val client = server.accept()!!


    val input = Scanner(client.getInputStream())
    val output = PrintWriter(client.getOutputStream())

    override fun chooseAction(current: State, states: List<State>): State {

        output.println(CardSerializer.encode(current))
        output.println(states.size)
        for(state in states)
            output.println(CardSerializer.encode(state))
        output.flush()
        val index = input.nextInt()
        println(index)
        return states[index]
    }
}


fun main(){
    val port = 24009
    runSocketConsole(port)
}

fun runSocketConsole(port: Int) {
    val socket = Socket("localhost", port)
    val input = Scanner(socket.getInputStream())
    val output = PrintWriter(socket.getOutputStream())
    println("run")
    while (true) {
        print("decode... ")
        val curr = CardSerializer.decode(input.nextLine())
        println(curr)
        val number = input.nextLine().toInt()
        for (i in 1..number) {
            val state = CardSerializer.decode(input.nextLine())
            println("$i: $state")
        }
        if (number == 1)
            output.println(0)
        else
            output.println(readLine()!!.toInt())
        output.flush()
    }
}