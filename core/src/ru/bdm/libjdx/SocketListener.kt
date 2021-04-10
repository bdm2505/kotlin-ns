package ru.bdm.libjdx

import com.badlogic.gdx.Gdx
import ru.bdm.mtg.BattleState
import ru.bdm.mtg.cards.CardSerializer
import java.io.PrintWriter
import java.net.Socket
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

class SocketListener(val mainScreen: MainScreen) : Thread() {
    val port = 24009
    val queue = LinkedBlockingQueue<Int>(10)

    fun put(index: Int) {
        queue.add(index)
    }

    init {
        isDaemon = true
    }

    override fun run() {
        val socket = Socket("localhost", port)
        val input = Scanner(socket.getInputStream())
        val output = PrintWriter(socket.getOutputStream())

        try {
            while (true) {
                val curr = CardSerializer.decode(input.nextLine())

                val number = input.nextLine().toInt()
                val states = (1..number).map {
                    CardSerializer.decode(input.nextLine())
                }
                println("read success!")
                Gdx.app.postRunnable {
                    if (states.size > 1) {
                        mainScreen.isUpdated = true
                    } else {
                        //sleep(500)
                    }
                    mainScreen.update(curr, states)
                    println("end runable")
                }
                if (states.size == 1)
                    output.println(0)
                else
                    output.println(queue.take())
                output.flush()
            }
        } catch (e: Exception){
            Gdx.app.exit()
        }
    }
}