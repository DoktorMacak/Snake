package com.semblergames.snake;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.GameData;

public class AndroidLauncher extends AndroidApplication {

	private static final String FILE_NAME = "tsnake_settings";

	private static final String PLAY_SOUNDS_PASS = "sounds";
	private static final String PLAY_MUSIC_PASS = "music";
	private static final String POINT_STARTS_PASS = "pointStars";
	private static final String HIGH_SCORE_PASS = "highScore";
	private static final String SHOW_TUTORIAL_PASS = "showTutorial";
	private static final String SNAKE_SPEED_PASS = "snakeSpeed";

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		config.useImmersiveMode = true;
		loadData();

		initialize(new main(), config);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	private void loadData(){
		try {
			SharedPreferences file = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
			GameData.HIGH_SCORE = file.getInt(HIGH_SCORE_PASS, 0);
			GameData.PLAY_MUSIC = file.getBoolean(PLAY_MUSIC_PASS, true);
			GameData.PLAY_SOUNDS = file.getBoolean(PLAY_SOUNDS_PASS, true);
			GameData.POINT_STARS = file.getInt(POINT_STARTS_PASS, 0);
			GameData.SHOW_TUTORIAL = file.getBoolean(SHOW_TUTORIAL_PASS, true);
			GameData.SNAKE_SPEED = file.getInt(SNAKE_SPEED_PASS, 1);

		}catch(Exception e){
			GameData.HIGH_SCORE = 0;
			GameData.PLAY_MUSIC = true;
			GameData.PLAY_SOUNDS = true;
			GameData.POINT_STARS = 0;
			GameData.SHOW_TUTORIAL = true;
			GameData.SNAKE_SPEED = 1;
		}
	}

	private void saveData(){

		try {

			SharedPreferences prefFile = getSharedPreferences(
					FILE_NAME,
					Context.MODE_PRIVATE
			);

			SharedPreferences.Editor editor = prefFile.edit();

			editor.putInt(HIGH_SCORE_PASS, GameData.HIGH_SCORE);
			editor.putBoolean(PLAY_MUSIC_PASS, GameData.PLAY_MUSIC);
			editor.putBoolean(PLAY_SOUNDS_PASS, GameData.PLAY_SOUNDS);
			editor.putInt(POINT_STARTS_PASS, GameData.POINT_STARS);
			editor.putBoolean(SHOW_TUTORIAL_PASS, GameData.SHOW_TUTORIAL);
			editor.putInt(SNAKE_SPEED_PASS, GameData.SNAKE_SPEED);

			editor.apply();

		}catch(Exception e){}

	}
}
