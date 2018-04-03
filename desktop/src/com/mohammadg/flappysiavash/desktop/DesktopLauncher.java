package com.mohammadg.flappysiavash.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mohammadg.flappysiavash.FlappySiavashGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = FlappySiavashGame.WIDTH;
		config.height = FlappySiavashGame.HEIGHT;
		config.title = FlappySiavashGame.TITLE;
		new LwjglApplication(new FlappySiavashGame(), config);
	}
}
