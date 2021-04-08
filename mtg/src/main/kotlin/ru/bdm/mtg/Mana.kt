package ru.bdm.mtg

enum class Mana(val char: Char) : Copied {
    NEUTRAL('C'),
    RED('R'),
    BLUE('U'),
    BLACK('B'),
    WHITE('W'),
    GREEN('G');
    override fun copy(): Mana {
        return this
    }
}

fun getMana(char: Char): Mana{
    return when(char){
        'C' -> Mana.NEUTRAL
        'R' -> Mana.RED
        'U' -> Mana.BLUE
        'B' -> Mana.BLACK
        'W' -> Mana.WHITE
        'G' -> Mana.GREEN

        else -> throw Exception("not color [$char]")
    }
}