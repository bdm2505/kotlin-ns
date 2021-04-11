package ru.bdm.libjdx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.bdm.libjdx.LibGdxStarter;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 60 * 15;
		config.height = 60 * 9;
		new LwjglApplication(new LibGdxStarter(), config);
	}
}
