package com.studios0110.doodles.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.studios0110.doodles.Source;
import com.studios0110.doodles.screens.Splash;
import com.studios0110.doodles.screens.Starter;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Splash.screenW;
		config.height = Splash.screenH;
		new LwjglApplication(new Starter(), config);
	}
}
