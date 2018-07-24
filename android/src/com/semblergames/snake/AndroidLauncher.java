package com.semblergames.snake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.GameData;
import com.semblergames.snake.utilities.ShareContent;

public class AndroidLauncher extends AndroidApplication implements ShareContent{

	private static final String FILE_NAME = "tsnake_settings";

	private static final String PLAY_SOUNDS_PASS = "sounds";
	private static final String PLAY_MUSIC_PASS = "music";
	private static final String POINT_STARTS_PASS = "pointStars";
	private static final String HIGH_SCORE_PASS = "highScore";
	private static final String SHOW_TUTORIAL_PASS = "showTutorial";
	private static final String SNAKE_SPEED_PASS = "snakeSpeed";
	private static final String SKIN_POINTER_PASS = "snakePointer";

	private static final int SKIN_AMOUNT = 33;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		config.useImmersiveMode = true;
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useGyroscope = false;
		loadData();

		initialize(new main(), config);
	}

	@Override
	protected void onStop() {
		super.onStop();
		saveData();
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
			GameData.SKIN_POINTER = file.getInt(SKIN_POINTER_PASS, 0);

			GameData.SKINS = new boolean[SKIN_AMOUNT];

			for(int i = 0; i < SKIN_AMOUNT; i++){
				if(i == 0){
					GameData.SKINS[i] = file.getBoolean("skin"+(i+1), true);
				}else {
					GameData.SKINS[i] = file.getBoolean("skin"+(i+1), false);
				}
			}

			GameData.SKIN_PRICES = new int[SKIN_AMOUNT];

			GameData.SKIN_PRICES[0] = 0;

			GameData.SKIN_PRICES[1] = 15;//15
			GameData.SKIN_PRICES[2] = 15;
			GameData.SKIN_PRICES[3] = 15;
			GameData.SKIN_PRICES[4] = 20;//20
			GameData.SKIN_PRICES[5] = 20;
			GameData.SKIN_PRICES[6] = 25;//25
			GameData.SKIN_PRICES[7] = 25;
			GameData.SKIN_PRICES[8] = 25;
			GameData.SKIN_PRICES[9] = 30;//30
			GameData.SKIN_PRICES[10] = 30;
			GameData.SKIN_PRICES[11] = 30;
			GameData.SKIN_PRICES[12] = 35;//35
			GameData.SKIN_PRICES[13] = 35;
			GameData.SKIN_PRICES[14] = 45;//45
			GameData.SKIN_PRICES[15] = 45;
			GameData.SKIN_PRICES[16] = 45;
			GameData.SKIN_PRICES[17] = 50;//50
			GameData.SKIN_PRICES[18] = 60;//60
			GameData.SKIN_PRICES[19] = 60;
			GameData.SKIN_PRICES[20] = 60;
			GameData.SKIN_PRICES[21] = 65;//65
			GameData.SKIN_PRICES[22] = 65;
			GameData.SKIN_PRICES[23] = 65;
			GameData.SKIN_PRICES[24] = 65;
			GameData.SKIN_PRICES[25] = 70;//70
			GameData.SKIN_PRICES[26] = 75;//75
			GameData.SKIN_PRICES[27] = 75;
			GameData.SKIN_PRICES[28] = 75;
			GameData.SKIN_PRICES[29] = 80;//80
			GameData.SKIN_PRICES[30] = 80;
			GameData.SKIN_PRICES[31] = 85;//85
			GameData.SKIN_PRICES[32] = 85;


		}catch(Exception e){
			GameData.HIGH_SCORE = 0;
			GameData.PLAY_MUSIC = true;
			GameData.PLAY_SOUNDS = true;
			GameData.POINT_STARS = 0;
			GameData.SHOW_TUTORIAL = true;
			GameData.SNAKE_SPEED = 1;
			GameData.SKIN_POINTER = 0;

			GameData.SKINS = new boolean[SKIN_AMOUNT];

			for(int i = 0; i < SKIN_AMOUNT; i++){
				if(i == 0){
					GameData.SKINS[i] = true;
				}else {
					GameData.SKINS[i] = false;
				}
			}

			GameData.SKIN_PRICES = new int[SKIN_AMOUNT];

			GameData.SKIN_PRICES[0] = 0;

			for(int i = 1; i < SKIN_AMOUNT; i++){
				GameData.SKIN_PRICES[i] = 10;
			}


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
			editor.putInt(SKIN_POINTER_PASS, GameData.SKIN_POINTER);

			for(int i = 0; i < SKIN_AMOUNT; i++){
				editor.putBoolean("skin"+(i+1), GameData.SKINS[i]);
			}


			editor.apply();

		}catch(Exception e){}

	}

	@Override
	public void shareScore(int score) {
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_TEXT, "Score: "+score+" points! Try to beat me now!");
		shareIntent.setType("text/plain");
		startActivity(shareIntent);
	}
}
