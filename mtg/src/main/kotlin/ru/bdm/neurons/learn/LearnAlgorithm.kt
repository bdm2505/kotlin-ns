package ru.bdm.neurons.learn

import ru.bdm.neurons.NeuronSystem
import java.util.*
import kotlin.math.abs

abstract class LearnAlgorithm {
    abstract val ns: NeuronSystem
    val sizeBatch = 1000
    var iterations: Int = 0
    var absError = Double.MAX_VALUE
    var minError = Double.MAX_VALUE
    var averageError = Double.MAX_VALUE
    private val listeners: LinkedList<NSListener> = LinkedList()
    open fun learnOne(input: ArrayList<Double>, rightAnswer: ArrayList<Double>){
        listeners.forEach { listener ->
            listener.listen(ns, this)
        }
    }

    fun learn(generator: TestGenerator, maxIterations: Int = Int.MAX_VALUE, minError: Double = 0.0) {
        initLearn(generator)
        while (maxIterations > iterations && absError > minError) {
            val (input, answer) = generator.nextOneLearn()
            learnOne(input, answer)
        }
        endLearn(generator)
    }

    private fun endLearn(generator: TestGenerator) {
        listeners.clear()
        println("end learn!")
    }

    fun initLearn(generator: TestGenerator){
        absError = getAbsError(ns, generator)
        addListener(NSCounterListener().apply {
            printData = { _, _ ->
                absError = getAbsError(ns, generator)
            }
        })
    }

    fun addListener(listener: NSListener) {
        listeners.add(listener)
    }

    fun calculateTestError(generator: TestGenerator) {
        absError = getAbsError(ns, generator)
    }

}

fun getAbsError(ns: NeuronSystem, generator: TestGenerator): Double {

    val tests = generator.nextTests()
    var sum = 0.0
    tests.forEach { (input, answer) ->
        sum += ns.work(input).zip(answer).map { (result, right) ->
            abs(result - right)
        }.sum()
    }
    return sum / tests.size
}
