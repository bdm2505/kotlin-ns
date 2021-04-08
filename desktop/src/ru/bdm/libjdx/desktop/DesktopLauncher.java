package ru.bdm.libjdx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.bdm.libjdx.LibGdxStarter;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 70 * 15;
		config.height = 70 * 9;
		Runnable runnable = () -> new LwjglApplication(new LibGdxStarter(), config);
		new Thread(runnable).start();
		new Thread(runnable).start();
	}
}
