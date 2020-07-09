package com.mobeigi.flappysiavash;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		int width = FlappySiavashGame.WIDTH;
		int height = FlappySiavashGame.HEIGHT;
		initialize(new FlappySiavashGame(), config);
	}
}
