package ru.bdm.gui

import java.awt.Dimension
import java.awt.Image
import java.awt.LayoutManager
import java.io.File
import javax.imageio.ImageIO
import javax.swing.*

class StartFrame {
}

fun main(){

    val frame = JFrame().apply {
        size = Dimension(50 * 13, 50 * 9)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        contentPane = JPanel()
    }
    val h = frame.height / 4

    val cardImage = ImageIO.read(File("card.jpg"))
    val newImage = cardImage.getScaledInstance(h * 2 / 3, h, Image.SCALE_DEFAULT)
    val icon = ImageIcon(newImage)
    val button = JLabel(icon)
    button.isVisible = true
    button.size = Dimension(200,300)

    frame.contentPane.add(button)
    frame.contentPane.add(JLabel(icon))


    SwingUtilities.invokeLater { frame.isVisible = true }

}