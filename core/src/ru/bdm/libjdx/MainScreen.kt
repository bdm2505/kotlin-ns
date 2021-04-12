package ru.bdm.libjdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import ru.bdm.mtg.BattleState
import ru.bdm.mtg.Card
import ru.bdm.mtg.Difference
import ru.bdm.mtg.StatePlayer
import ru.bdm.mtg.cards.creatures.Creature

class MainScreen : BaseScreen() {

    val W: Float
        get() = Gdx.graphics.width.toFloat()
    val H: Float
        get() = Gdx.graphics.height.toFloat()

    var battleState: BattleState = BattleState()
    var stt = "ass"
    lateinit var listener: SocketListener
    lateinit var states: List<BattleState>
    lateinit var diffs: List<Pair<Difference, Difference>>
    var isUpdated = false

    private fun drawable(path:String) = TextureRegionDrawable(TextureRegion(Texture(path)))
    val buttonStyle = TextButton.TextButtonStyle(drawable("white.png"), drawable("green.png"), drawable("green.png"), BitmapFont().apply { color = Color.BLACK })

    fun update(curr: BattleState, battleStates: List<BattleState>) {
        println("update")
        battleState = curr.clone()
        states = battleStates
        diffs = battleStates.map { st -> battleState.getDifference(st) }
        stage.addActor(CardActor(Creature(), 100f,100f))
        updateStage(battleState)
    }

    fun pick(id: Int) {
        if (!isUpdated)
            return

        for(index in diffs.indices){
            val (diff1, diff2) = diffs[index]
            if(diff1.changes.map { it.first.id }.contains(id) || diff2.changes.map { it.first.id }.contains(id)){
                listener.put(index)
                update(states[index], listOf())
                isUpdated = false
                break
            }
        }
    }
    val labelStyle = Label.LabelStyle(BitmapFont(), Color.WHITE)

    private fun updateStage(state: BattleState) {
        stage.clear()
        println(state.me)
        stage.addActor(HorizontalGroup().apply {
            val h = H / 4
            setBounds(0f, 0f, W - 100, h)
            for (id in state.me.hand) {
                addMyActor(state.me, id, h)
            }
        })

        stage.addActor(HorizontalGroup().apply {
            val h = H / 4
            setBounds(0f, h, W - 100, h)
            for (id in state.me.battlefield) {
                addMyActor(state.me, id, h)
            }
        })
        stage.addActor(HorizontalGroup().apply {
            val h = H / 4
            setBounds(0f, 2 * h, W - 100, h)
            for (id in state.enemy.battlefield) {
                addMyActor(state.enemy, id, h)
            }
        })
        stage.addActor(HorizontalGroup().apply {
            val h = H / 4
            setBounds(0f, 3 * h, W - 100, h)
            for (id in state.enemy.hand) {

                addMyActor(state.enemy, id, h)
            }
        })
        stage.addActor(addPlayerInfo(state.me, 0f))
        stage.addActor(addPlayerInfo(state.enemy, H / 2))
        stage.addActor(TextButton("NEXT", buttonStyle).apply {
            setBounds(W - 100, 0f,100f,30f)
            addListener(object : ClickListener(){
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    if(isUpdated) {
                        listener.put(states.size - 1)
                        update(states.last(), listOf())
                        isUpdated = false
                    }
                }
            })
        })

    }

    private fun addPlayerInfo(player: StatePlayer, y: Float): VerticalGroup {
        return VerticalGroup().apply {
            setBounds(W - 100, y, 100f, H / 2)
            addActor(Label("${player.phase}", labelStyle).apply { color = Color.GREEN })
            addActor(Label("${player.hp}hp", labelStyle).apply { color = Color.RED })
            player.graveyard.map { player(it) }.forEach {
                addActor(CardActor(it as Card, 100f, 30f))
            }
        }
    }

    private fun HorizontalGroup.addMyActor(state: StatePlayer, id: Int, h: Float) {
        addActor(CardActor(state(id) as Card, h * 2 / 3, h).apply {
            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    println("clicked $id")
                    pick(card.id)
                }
            })
        })
    }
}