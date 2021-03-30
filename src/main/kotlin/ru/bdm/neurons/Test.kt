package ru.bdm.neurons

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.ui.ApplicationFrame
import org.jfree.data.time.Millisecond
import org.jfree.data.time.TimeSeries
import org.jfree.data.time.TimeSeriesCollection
import org.jfree.data.xy.XYDataset
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JPanel


/**
 * A demonstration application showing a time series chart where you can dynamically add
 * (random) data by clicking on a button.
 *
 */
class DynamicDataDemo(title: String?) : ApplicationFrame(title), ActionListener {
    /** The time series data.  */
    private val series: TimeSeries

    /** The most recent value added.  */
    private var lastValue = 100.0

    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset.
     *
     * @return A sample chart.
     */
    private fun createChart(dataset: XYDataset): JFreeChart {
        val result = ChartFactory.createTimeSeriesChart(
            "Dynamic Data Demo",
            "Time",
            "Value",
            dataset,
            true,
            true,
            false
        )
        val plot = result.xyPlot
        var axis = plot.domainAxis
        axis.isAutoRange = true
        axis.fixedAutoRange = 60000.0 // 60 seconds
        axis = plot.rangeAxis
        axis.setRange(0.0, 200.0)
        return result
    }
    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    *
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    /**
     * Handles a click on the button by adding new (random) data.
     *
     * @param e  the action event.
     */
    override fun actionPerformed(e: ActionEvent) {
        if (e.actionCommand == "ADD_DATA") {
            val factor = 0.90 + 0.2 * Math.random()
            lastValue = lastValue * factor
            val now = Millisecond()
            println("Now = $now")
            series.add(Millisecond(), lastValue)
        }
    }

    companion object {
        /**
         * Starting point for the demonstration application.
         *
         * @param args  ignored.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val demo = DynamicDataDemo("Dynamic Data Demo")
            demo.pack()
            demo.isVisible = true
        }
    }

    /**
     * Constructs a new demonstration application.
     *
     * @param title  the frame title.
     */
    init {
        series = TimeSeries("Random Data")
        val dataset = TimeSeriesCollection(series)
        val chart = createChart(dataset)
        val chartPanel = ChartPanel(chart)
        val button = JButton("Add New Data Item")
        button.actionCommand = "ADD_DATA"
        button.addActionListener(this)
        val content = JPanel(BorderLayout())
        content.add(chartPanel)
        content.add(button, BorderLayout.SOUTH)
        chartPanel.preferredSize = Dimension(500, 270)
        contentPane = content
    }
}