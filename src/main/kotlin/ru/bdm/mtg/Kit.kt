package ru.bdm.mtg


typealias Kit<T> = HashMap<T, Int>

fun <T> emptyKit(): Kit<T> = HashMap()
fun <T> kitOf(vararg pairs: Pair<T, Int>): Kit<T> = mutableMapOf(*pairs) as HashMap
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

fun String.toCost(): Kit<Mana> {
    val kit = emptyKit<Mana>()
    this.map{ getMana(it) }.forEach(kit::add)
    return kit
}


fun <T> Kit<T>.add(element: T){
    get(element)?.let{
        put(element, get(element)!! + 1)
    } ?: run { this[element] = 1 }
}

fun <T> Kit<T>.take(element: T) {
    get(element)?.let {
        if (it > 1)
            put(element, it - 1)
        else
            remove(element)
    }
}
fun<K> Kit<K>.counts(): Int {
    return this.map{ it.value }.sum()
}
fun<T> Kit<T>.count(elem: T): Int {
    return get(elem) ?: 0
}

interface Copied {
    fun copy(): Any
}


