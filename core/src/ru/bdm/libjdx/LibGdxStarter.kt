package ru.bdm.libjdx

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20


class LibGdxStarter : Game() {


    override fun create() {
        val mainScreen = MainScreen()
        val listener = SocketListener(mainScreen)
        mainScreen.listener = listener
        listener.start()
        setScreen(mainScreen)
//        setScreen(BaseScreen())

    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        super.render()
    }


}