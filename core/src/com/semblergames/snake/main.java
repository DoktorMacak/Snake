package com.semblergames.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.gamePackage.GameOverState;
import com.semblergames.snake.gamePackage.GameState;
import com.semblergames.snake.gamePackage.PlayState;
import com.semblergames.snake.utilities.ChangeState;

public class main extends ApplicationAdapter implements InputProcessor, ChangeState{

    public static final int PLAY_STATE = 0;
    public static final int GAME_OVER_STATE = 1;


	public static float SCALEX;
	public static float SCALEY;

	public static float WIDTH;
	public static float HEIGHT;

	public static float BLOCK_WIDTH = 60;
	public static float BLOCK_HEIGHT = 60;

	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;


	private GameState[] states = new GameState[2];

	private GameState currentState;


	private float alpha;

	private SpriteBatch batch;
	private ShapeRenderer renderer;


	@Override
	public void create () {

		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		SCALEX = WIDTH / (float)1080;
		SCALEY = HEIGHT / (float)1920;

		BLOCK_WIDTH *= SCALEX;
		BLOCK_HEIGHT *= SCALEY;

		SCREEN_WIDTH = 18;
		SCREEN_HEIGHT = 32;

		batch = new SpriteBatch();
		renderer = new ShapeRenderer();



		states[0] = new PlayState();
		states[1] = new GameOverState();

		for(GameState state:states){
			state.init();
			state.setChangeListener(this);
		}

		currentState = states[0];

		alpha = 1f;


		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		currentState.render(batch, renderer, alpha, Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		for(GameState state:states){
			state.dispose();
		}
		batch.dispose();
		renderer.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
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
		int y = (int)HEIGHT - screenY;
		currentState.touchDown(screenX, y);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		int y = (int)HEIGHT - screenY;
		currentState.touchUp(screenX, y);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
//		int y = (int)HEIGHT - screenY;
//		playState.touchDragged(prevX, prevX, screenX, y);
//
//		prevX = screenX;
//		prevY = y;
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
		currentState = states[x];
	}
}
