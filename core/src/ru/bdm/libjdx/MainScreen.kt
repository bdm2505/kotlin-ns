package ru.bdm.libjdx

import com.badlogic.gdx.Files
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

class MainScreen : ScreenAdapter() {

    val stage: Stage = Stage()
    val card: Drawable = TextureRegionDrawable(TextureRegion( Texture(Gdx.files.internal("card.jpg"))))
    val W:Float
        get() = Gdx.graphics.width.toFloat()
    val H:Float
        get() = Gdx.graphics.height.toFloat()

    override fun show() {
        val box = HorizontalGroup().apply {

            setBounds(0f,0f, W, H / 3)
            card.minWidth = card.minWidth / card.minHeight * height
            card.minHeight = height


            addActor(Image(card))
            addActor(ImageButton(card))
        }
        stage.addActor(box)
    }

    override fun render(delta: Float) {
        super.render(delta)
        stage.draw()
        stage.act()
    }
}