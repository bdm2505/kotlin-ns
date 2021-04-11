package ru.bdm.mtg


typealias Kit<T> = HashMap<T, Int>

fun <T> emptyKit(): Kit<T> = HashMap()
//fun <T> kitOf(vararg pairs: Pair<T, Int>): Kit<T> = mutableMapOf(*pairs) as HashMap
fun <T> dequeOf(vararg elements: T): ArrayDeque<T> = ArrayDeque(elements.toList())
fun <T> Kit<T>.copy(): Kit<T> where T : Copied {
    return HashMap(this.map { Pair(it.key.copy() as T, it.value) }.toMap())
}



fun <T> MutableList<T>.copy(): MutableList<T> where T : Copied {
    return this.map { it.copy() as T }.toMutableList()
}

fun <T> ArrayDeque<T>.copy(): ArrayDeque<T> where T : Copied {
    return ArrayDeque(this.map { it.copy() as T })
}

fun String.toMana(): Kit<Mana> {
    val kit = emptyKit<Mana>()
    this.map { getMana(it) }.forEach(kit::plus)
    return kit
}


operator fun <T> Kit<T>.plus(element: T): Kit<T> {
    get(element)?.let {
        put(element, get(element)!! + 1)
    } ?: run { this[element] = 1 }
    return this
}

operator fun <T> Kit<T>.plusAssign(coll: Kit<T>) {
    for ((color, count) in coll) {
        if (containsKey(color))
            this[color] = this[color]!! + count
        else
            this[color] = count
    }
}

operator fun <T> Kit<T>.minus(element: T): Kit<T>{
    get(element)?.let {
        if (it > 1)
            put(element, it - 1)
        else
            remove(element)
    }
    return this
}

fun<K> Kit<K>.counts(): Int {
    return this.map{ it.value }.sum()
}
fun<T> Kit<T>.count(elem: T): Int {
    return get(elem) ?: 0
}

fun<T> MutableSet<T>.copy(): MutableSet<T> where T : Copied {
    return this.map { it.copy() as T }.toMutableSet()
}

fun Kit<Mana>.string(): String {
    val sb = StringBuilder()
    for((ch, count) in this){
        for(i in 1..count)
            sb.append(ch.char)
    }
    return sb.toString()
}


interface Copied {
    fun copy(): Any
}




