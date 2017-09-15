package com.semblergames.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.gamePackage.GameOverState;
import com.semblergames.snake.gamePackage.GameState;
import com.semblergames.snake.gamePackage.LoadState;
import com.semblergames.snake.gamePackage.MainMenuState;
import com.semblergames.snake.gamePackage.PlayState;
import com.semblergames.snake.gamePackage.SettingsState;
import com.semblergames.snake.gamePackage.ShopState;
import com.semblergames.snake.gamePackage.TutorialState;
import com.semblergames.snake.utilities.ChangeState;
import com.semblergames.snake.utilities.GameData;
import com.semblergames.snake.utilities.Image;
import com.semblergames.snake.utilities.Playlist;
import com.semblergames.snake.utilities.Skin;

public class main extends ApplicationAdapter implements InputProcessor, ChangeState{

    public static final int LOAD_STATE = 0;
	public static final int MAIN_MENU_STATE = 1;
	public static final int PLAY_STATE = 2;
    public static final int SETTINGS_STATE = 3;
	public static final int SHOP_STATE = 4;
	public static final int TUTORIAL_STATE = 5;
	public static final int GAME_OVER_STATE = 6;


	public static float SCALEX;
	public static float SCALEY;

	public static float WIDTH;
	public static float HEIGHT;

	public static float BLOCK_WIDTH;
	public static float BLOCK_HEIGHT;

	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;



	private final float alphaRatio = 2.3f;


	private GameState[] states;

	private int nextIndex;
	private int currentIndex;
	private boolean ready;


	private float alpha;

	/**
	 * muzika
	 */

	private Playlist playlist;

	private Sound click;

	/**
	 Sve za renderovanje
	 */

	private SpriteBatch batch;
	private ShapeRenderer renderer;
	private FreeTypeFontGenerator fontGenerator;


	@Override
	public void create () {

		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		SCALEX = WIDTH / 1080f;
		SCALEY = HEIGHT / 1920f;

		BLOCK_WIDTH = 60f * SCALEX;
		BLOCK_HEIGHT = 60f * SCALEY;

		SCREEN_WIDTH = 18;
		SCREEN_HEIGHT = 32;

		batch = new SpriteBatch();
		renderer = new ShapeRenderer();
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/MAHAWA.TTF"));

		states = new GameState[7];

		states[LOAD_STATE] = new LoadState();
		states[LOAD_STATE].initTexturesAndFonts(fontGenerator);
		states[LOAD_STATE].setChangeListener(this);
		states[LOAD_STATE].init();

		states[MAIN_MENU_STATE] = new MainMenuState();
		states[MAIN_MENU_STATE].initTexturesAndFonts(fontGenerator);
		states[MAIN_MENU_STATE].setChangeListener(this);

		states[PLAY_STATE] = new PlayState();
		states[PLAY_STATE].initTexturesAndFonts(fontGenerator);
		states[PLAY_STATE].setChangeListener(this);

		states[SETTINGS_STATE] = new SettingsState();
		states[SETTINGS_STATE].initTexturesAndFonts(fontGenerator);
		states[SETTINGS_STATE].setChangeListener(this);

		states[SHOP_STATE] = new ShopState();
		states[SHOP_STATE].initTexturesAndFonts(fontGenerator);
		states[SHOP_STATE].setChangeListener(this);

		states[TUTORIAL_STATE] = new TutorialState();
		states[TUTORIAL_STATE].initTexturesAndFonts(fontGenerator);
		states[TUTORIAL_STATE].setChangeListener(this);

		states[GAME_OVER_STATE] = new GameOverState();
		states[GAME_OVER_STATE].initTexturesAndFonts(fontGenerator);
		states[GAME_OVER_STATE].setChangeListener(this);

		currentIndex = LOAD_STATE;
		nextIndex = LOAD_STATE;
		ready = true;

		alpha = 1f;

		playlist = new Playlist();

		click = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));

		Gdx.input.setInputProcessor(this);

		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float delta = Gdx.graphics.getDeltaTime();

		if(nextIndex != currentIndex && alpha > 0){
			alpha -= delta*alphaRatio;
			if(alpha < 0){
				alpha = 0;
			}
			if(alpha == 0 && ready){
				currentIndex = nextIndex;
			}
		}

		if(nextIndex == currentIndex && alpha < 1){
			alpha += delta*alphaRatio;
			if(alpha > 1){
				alpha = 1f;
			}
		}

		Color color = batch.getColor();

		batch.setColor(color.r, color.g, color.b, alpha);

		states[currentIndex].render(batch, renderer, alpha, delta);

		playlist.update(delta);
		batch.begin();
		playlist.draw(batch);
		batch.end();

	}
	
	@Override
	public void dispose () {
		for(GameState state:states){
			state.dispose();
		}
		fontGenerator.dispose();
		batch.dispose();
		renderer.dispose();
		playlist.dispose();
		click.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.BACK){
			states[currentIndex].backPressed();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		states[currentIndex].touchDown(screenX, (int)HEIGHT - screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		states[currentIndex].touchUp(screenX, (int)HEIGHT - screenY);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		states[currentIndex].touchDragged(screenX, (int)HEIGHT - screenY);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public void changeState(int x) {

		if(x != nextIndex) {
			nextIndex = x;

			new Thread(new Runnable() {
				@Override
				public void run() {
					ready = false;
					states[nextIndex].init();
					ready = true;
				}
			}).start();
		}
	}

	@Override
	public void playMusic() {
		playlist.play();
	}

	@Override
	public void stopMusic() {
		playlist.pause();
	}

	@Override
	public void playClicked() {
		if(GameData.PLAY_SOUNDS)
		click.play();
	}
}
