package ru.bdm.libjdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import ru.bdm.mtg.Card
import ru.bdm.mtg.RotateCard
import ru.bdm.mtg.cards.Creature

class CardActor(val card: Card, width: Float, height:Float) : Group() {

    val image = Image(TextureRegionDrawable(TextureRegion( Texture(Gdx.files.internal("card.jpg")))))
    val style =  Label.LabelStyle(BitmapFont(), Color.BLACK)
    val name = Label(card.name, style)

    init {
        setSize(width, height)
        image.setSize(width, height)
        name.setPosition(width / 2 - name.prefWidth / 2, height - 5 - name.prefHeight)
        addActor(image)
        addActor(name)
        if(card is RotateCard && card.rotated){
            setOrigin(Align.center)
            rotateBy(-20f)
        }
        if(card is Creature){
            val hp = Label("${card.force}/${card.hp}", style)
            hp.setPosition(width - hp.prefWidth  - 10, 10f)
            addActor(hp)
        }
    }




}