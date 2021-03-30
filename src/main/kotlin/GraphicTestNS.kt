@file:Suppress("UNCHECKED_CAST")

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import ru.bdm.neurons.NeuronSystem
import ru.bdm.neurons.Rand
import ru.bdm.neurons.functions.*
import ru.bdm.neurons.learn.*
import javax.swing.JFrame
import javax.swing.SwingUtilities
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sin


class GraphicTestNS(
    val alg: LearnAlgorithm,
    val generator: TestGenerator,
    val inputGenerator: TestGenerator = generator
) {
    val seriesRight = XYSeries("right")
    val seriesAnswer = XYSeries("answer")
    val seriesError = XYSeries("error")
    val dataSet = XYSeriesCollection()
    val chart = ChartFactory.createXYLineChart("title", "x", "y", dataSet)

    init {
        dataSet.addSeries(seriesError)
        dataSet.addSeries(seriesAnswer)
        dataSet.addSeries(seriesRight)
        chart.xyPlot.domainAxis.apply {
            isAutoRange = true
            fixedAutoRange = 100.0
        }
    }

    val frame = JFrame("MinimalStaticChart").apply {
        contentPane.add(ChartPanel(chart))
        setSize(1200, 700)
        isVisible = true
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    }

    var index = 0
    var currentTime = System.currentTimeMillis()

    fun isTime(): Boolean {
        return if (System.currentTimeMillis() - currentTime > 200) {
            currentTime = System.currentTimeMillis()
            true
        } else
            false
    }

    fun run() {
        while (frame.isVisible) {


            oneLearn()
            if (isTime()) {
                alg.calculateTestError(generator)
                val neuronSystem = alg.ns.copy()
                index++
                SwingUtilities.invokeLater {
                    printData(neuronSystem)
                }
            }
        }

    }

    fun printData(ns: NeuronSystem) {
        val error = getAbsError(ns, inputGenerator)
        seriesError.add(index, error)
        val (inputTest, answerTest) = inputGenerator.nextOneLearn()
        seriesRight.add(index, answerTest[0])
        seriesAnswer.add(index, ns.work(inputTest)[0])
    }

    fun oneLearn() {
        val (input, answer) = generator.nextOneLearn()

        alg.learnOne(input, answer)
    }
}

fun main(arg: Array<String>) {
    //runXorLearning()
runSinLearning()

}

fun runSinLearning(){
    fun nextRandInput() = arrayListOf(Rand.rand.nextDouble() * PI * 2)
    fun right(x: Double) = abs(sin(sin(x) - 2.0))

    val generator = FromFunctionsGenerator({
        nextRandInput()
    }, { array ->
        arrayListOf(right(array[0]))
    }, (1..1000).map { nextRandInput() })
    var currentDelta = 0.0
    val drawingGenerator = FromFunctionsGenerator({
        currentDelta += 0.2
        arrayListOf(currentDelta % (PI * 2))
    }, { array ->
        arrayListOf(right(array[0]))
    }, (1..1000).map { nextRandInput() })

    val ns = NeuronSystem(ReLu(), 1, 40, 20, 10, 1)
    val alg = BackPropagation(ns).apply {
        speedLearn = 0.001
        addListener(PrinterCounterListener(10000))
    }
    GraphicTestNS(alg, generator, drawingGenerator).run()
}

private fun runXorLearning() {
    println("run xor learning start ...")
    val batch = arrayListOf(
        Pair(arrayListOf(.0, .0), arrayListOf(1.0)),
        Pair(arrayListOf(.0, 1.0), arrayListOf(.0)),
        Pair(arrayListOf(1.0, .0), arrayListOf(.0)),
        Pair(arrayListOf(1.0, 1.0), arrayListOf(1.0))
    )


    val ns = NeuronSystem(ReLu(), 2, 40, 40, 20, 1)
    val alg = BackPropagation(ns).apply {
        speedLearn = 0.001
        addListener(PrinterCounterListener(10000))
    }
    val generator = ArrayGenerator(batch)
    GraphicTestNS(alg, generator).run()
}

