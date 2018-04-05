package com.mohammadg.flappysiavash;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.surfaceview.RatioResolutionStrategy;
import com.mohammadg.flappysiavash.FlappySiavashGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		int width = FlappySiavashGame.WIDTH;
		int height = FlappySiavashGame.HEIGHT;
		config.resolutionStrategy = new RatioResolutionStrategy(width, height);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		initialize(new FlappySiavashGame(), config);
	}
}
