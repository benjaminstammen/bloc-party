package com.blocparty.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.blocparty.game.BlocParty;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		String version = "v1.3";

		config.width = 950;
		config.height = 500;

//		config.width = 1250;
//		config.height = 800;

//		config.width = 1;
//		config.height = 1;

		config.title = "BlocParty " + version;
		config.samples = 8; //for anti-aliasing / smoothness of lines

		//new LwjglApplication(new MyGdxGame(), config);
		//new LwjglApplication(new GameClass(), config);
		new LwjglApplication(BlocParty.getInstance(), config);
	}
}
