package ru.bdm.neurons.learn

import ru.bdm.neurons.Rand

typealias TestValues = Pair<ArrayList<Double>, ArrayList<Double>>


interface TestGenerator {
    fun nextOneLearn(): TestValues

    fun nextTests(): List<TestValues>
}


class FromFunctionsGenerator(
    val inputGenerator: () -> ArrayList<Double>,
    val rightFun: (ArrayList<Double>) -> ArrayList<Double>,
    testInput: List<ArrayList<Double>>
) : TestGenerator {
    private val nextTests = testInput.map { input -> Pair(input, rightFun(input)) }

    override fun nextOneLearn(): TestValues {
        val input = inputGenerator()
        return Pair(input, rightFun(input))
    }

    override fun nextTests(): List<TestValues> {
        return nextTests
    }

}

class ArrayGenerator(val batch: List<TestValues>, val testBatch: List<TestValues> = batch) : TestGenerator{
    override fun nextOneLearn(): TestValues {
        return batch[Rand.nextInt(batch.size)]
    }

    override fun nextTests(): List<TestValues> {
        return testBatch
    }
}