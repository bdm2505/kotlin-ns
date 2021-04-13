package ru.bdm.mtg

import java.io.PrintWriter
import java.util.*

class CardWriter(val writer: PrintWriter) {
    fun write(card: AbstractCard) {
        writer.print(card::class.qualifiedName)
        writer.print(' ')
        writer.println(card.writeData())
    }

    fun writeStatePlayer(st: StatePlayer) {

    }
}

class CardReader(val reader: Scanner) {

    fun read(): AbstractCard {
        val className = reader.next()
        val inst = Class.forName(className).getConstructor().newInstance() as AbstractCard
        inst.readData(reader.nextLine())
        return inst
    }
}