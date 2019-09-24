package de.flutze.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.flutze.TheGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Earth Invaders";
		config.width = 896;
		config.height = 504;
		config.fullscreen = false;
		new LwjglApplication(new TheGame(), config);
	}
}
